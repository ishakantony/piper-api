package app.piper.piper.pipeline;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_bulk/all-pipelines-with-stats")
@RequiredArgsConstructor
public class PipelineBulkController {

    private final PipelineWithStatsService pipelineWithStatsService;

    @GetMapping
    public Set<PipelineWithStats> getAllPipelinesWithStats() {
        return pipelineWithStatsService.getAllPipelinesWithStats();
    }

}
