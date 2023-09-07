package app.piper.piper.web;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Request body missing or invalid
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseBody.builder().code(ErrorCode.MISSING_OR_INVALID_REQUEST_BODY)
                        .error("Missing or invalid request body").error(ex.getMessage()).build());
    }

    // Standard client error
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> httpClientErrorExceptionHandler(HttpClientErrorException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(ErrorResponseBody.builder().code(ErrorCode.CLIENT_ERROR).error(ex.getMessage()).build());
    }

    // Standard server error
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<?> httpServerErrorExceptionHandler(HttpServerErrorException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponseBody.ErrorResponseBodyBuilder builder = ErrorResponseBody.builder().code(ErrorCode.SERVER_ERROR)
                .error(ex.getMessage());

        if (log.isDebugEnabled()) {
            builder.stackTrace(ExceptionUtils.getStackTrace(ex));
        }

        return ResponseEntity.status(ex.getStatusCode()).body(builder.build());
    }

    // Handles validation error thrown by @Validated
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid format for " + ex.getName();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseBody.builder().code(ErrorCode.INVALID_ARGUMENT).error(errorMessage).build());
    }

    // Handles validation errors thrown by @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> formattedFieldErrors = getFormattedFieldErrors(fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseBody.builder().code(ErrorCode.INVALID_FIELDS).errors(formattedFieldErrors).build());
    }

    // Use this exception always
    @ExceptionHandler(PiperException.class)
    public ResponseEntity<ErrorResponseBody> handleUncaughtPiperException(PiperException ex) {
        if (ex.getStatus().is5xxServerError()) {
            log.error(ex.getMessage(), ex);
        }

        ErrorResponseBody.ErrorResponseBodyBuilder builder = ErrorResponseBody.builder().code(ex.getCode())
                .errors(ex.getErrors());

        if (log.isDebugEnabled()) {
            builder.stackTrace(ExceptionUtils.getStackTrace(ex));
        }

        return ResponseEntity.status(ex.getStatus()).body(builder.build());
    }

    // Root of all exceptions, if any handlers can't handle, goes here
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseBody> handleUncaughtRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponseBody.ErrorResponseBodyBuilder builder = ErrorResponseBody.builder()
                .code(ErrorCode.RUNTIME_EXCEPTION).error(ex.getMessage());

        if (log.isDebugEnabled()) {
            builder.stackTrace(ExceptionUtils.getStackTrace(ex));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
    }

    private List<String> getFormattedFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> "Invalid field [" + fieldError.getField() + "], " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
    }

}
