package app.piper.piper.operation.bulk;

import static app.piper.piper.operation.bulk.BulkOperationNames.COLLECT_ALL_PIPELINES_STATS;
import static app.piper.piper.operation.bulk.BulkOperationNames.GET_ALL_PIPELINES_STATS;

import app.piper.piper.pipeline.PipelineWithStatsService;
import app.piper.piper.pipeline.stats.PipelineStatsBulkService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_bulk")
@RequiredArgsConstructor
public class BulkOperationController {

    private final PipelineWithStatsService pipelineWithStatsService;

    private final PipelineStatsBulkService pipelineStatsBulkService;

    private static final Map<String, Boolean> DICTIONARY = new HashMap<>() {
        {
            put(GET_ALL_PIPELINES_STATS, false);
            put(COLLECT_ALL_PIPELINES_STATS, true);
        }
    };

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> executeBulkOperation(@Valid @RequestBody BulkOperationRequest request) {
        if (!DICTIONARY.containsKey(request.getOperation())) {
            throw new BulkOperationNotSupportedException();
        }

        if (BulkExecutionMode.ASYNC.equals(request.getMode()) && !this.supportAsync(request.getOperation())) {
            throw new BulkOperationNotSupportedException();
        }

        // TODO - Support async

        switch (request.getOperation()) {
            case GET_ALL_PIPELINES_STATS -> {
                return ResponseEntity.ok(pipelineWithStatsService.getAllPipelinesWithStats());
            }
            case COLLECT_ALL_PIPELINES_STATS -> pipelineStatsBulkService.collectStatsForAllPipelines();
        }

        return ResponseEntity.ok().build();
    }

    private boolean supportAsync(String operationName) {
        return DICTIONARY.getOrDefault(operationName, false);
    }

}
