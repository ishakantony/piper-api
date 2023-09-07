package app.piper.piper.pipeline.instance;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class PipelineInstanceNotFoundException extends PiperException {

    private static final String ERROR_MESSAGE = "The instance you are looking for doesn't exist";

    public PipelineInstanceNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
