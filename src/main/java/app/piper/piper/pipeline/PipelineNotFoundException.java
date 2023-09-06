package app.piper.piper.pipeline;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class PipelineNotFoundException extends PiperException {

    private static final String ERROR_MESSAGE = "Pipeline not found";

    public PipelineNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
