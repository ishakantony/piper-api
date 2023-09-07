package app.piper.piper.pipeline.stats;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class PipelineStatsCollectException extends PiperException {

    private static final String ERROR_MESSAGE = "This pipeline has no instances, stats cannot be generated";

    public PipelineStatsCollectException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
