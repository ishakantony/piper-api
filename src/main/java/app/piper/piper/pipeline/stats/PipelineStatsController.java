package app.piper.piper.pipeline.stats;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipelines/{pipelineId}/stats")
@RequiredArgsConstructor
public class PipelineStatsController {

    private final PipelineStatsService pipelineStatsService;

    @PostMapping("/collect")
    public ResponseEntity<PipelineStatsResponse> collectStats(@PathVariable UUID pipelineId) {
        return ResponseEntity.ok(pipelineStatsService.collectStats(pipelineId));
    }

    @GetMapping
    public ResponseEntity<PipelineStatsResponse> getStats(@PathVariable UUID pipelineId) {
        return ResponseEntity.ok(pipelineStatsService.getStats(pipelineId));
    }

}
