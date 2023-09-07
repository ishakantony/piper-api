package app.piper.piper.pipeline.instance;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class InvalidPipelineInstanceException extends PiperException {

    private static final String ERROR_MESSAGE = "Invalid pipeline instance. Please check your request.";

    public InvalidPipelineInstanceException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
