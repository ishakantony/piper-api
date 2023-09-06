package app.piper.piper.pipeline.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_bulk/collect-stats-for-all-pipelines")
@RequiredArgsConstructor
public class PipelineStatsBulkController {

    private final PipelineStatsBulkService pipelineStatsBulkService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void collectStatsForAllPipeline() {
        pipelineStatsBulkService.collectStatsForAllPipelines();
    }

}
