package app.piper.piper.pipeline;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class InvalidPipelineException extends PiperException {

    private static final String ERROR_MESSAGE = "Pipeline is invalid, either it doesn't exist or incorrect";

    public InvalidPipelineException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
