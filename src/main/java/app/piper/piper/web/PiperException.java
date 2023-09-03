package app.piper.piper.web;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * General exception for Piper related errors, whenever possible please use this
 * exception, or at least extend it.
 * </p>
 *
 * <p>
 * You may provide {@link HttpStatus} and a custom error code which is just a
 * {@link String}. Other than that, you can add one or more error messages.
 * </p>
 *
 * <p>
 * <strong>We recommend</strong> using the following format for code -> PIPER-[NUMBER]
 * </p>
 *
 * <p>
 * For example: PIPER-777, PIPER-109
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public abstract class PiperException extends RuntimeException {

    public PiperException(HttpStatus status, String code, List<String> errors, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
        this.errors = errors;
    }

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    private String code = ErrorCode.UNSPECIFIED_ERROR_CODE;

    @Singular
    public List<String> errors;

}
