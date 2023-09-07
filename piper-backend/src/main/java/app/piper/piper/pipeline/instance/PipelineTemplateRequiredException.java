package app.piper.piper.pipeline.instance;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class PipelineTemplateRequiredException extends PiperException {

    private static final String ERROR_MESSAGE = "Pipeline template is required to create a pipeline instance, create one and try again.";

    public PipelineTemplateRequiredException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
