package app.piper.piper.pipeline.stats;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipelines")
@RequiredArgsConstructor
public class PipelineStatsController {

    private final PipelineStatsService pipelineStatsService;

    @PostMapping("/{pipelineId}/stats/collect")
    public ResponseEntity<PipelineStatsResponse> collectStats(@PathVariable UUID pipelineId) {
        return ResponseEntity.ok(pipelineStatsService.collectStats(pipelineId));
    }

}
