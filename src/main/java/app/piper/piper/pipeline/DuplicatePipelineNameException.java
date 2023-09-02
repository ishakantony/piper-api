package app.piper.piper.pipeline;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatePipelineNameException extends RuntimeException {

    public static final String DB_CONSTRAINT_NAME = "UQ_PIPELINE_NAME";

    public DuplicatePipelineNameException(Throwable cause) {
        super("Pipeline name must be unique", cause);
    }

}
