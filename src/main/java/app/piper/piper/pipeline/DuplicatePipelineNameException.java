package app.piper.piper.pipeline;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class DuplicatePipelineNameException extends PiperException {

    public static final String DB_CONSTRAINT_NAME = "UQ_PIPELINE_NAME";

    private static final String ERROR_MESSAGE = "Pipeline name must be unique";

    public DuplicatePipelineNameException(Throwable cause) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CONSTRAINT_VIOLATION, List.of(ERROR_MESSAGE), ERROR_MESSAGE, cause);
    }

}
