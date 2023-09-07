package app.piper.piper.pipeline.stats;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class PipelineStatsNotFoundException extends PiperException {

    private static final String ERROR_MESSAGE = "This pipeline doesn't have any stats";

    public PipelineStatsNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
