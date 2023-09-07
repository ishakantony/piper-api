package app.piper.piper.reporter;

import app.piper.piper.common.Health;
import app.piper.piper.pipeline.PipelineWithStats;
import app.piper.piper.pipeline.PipelineWithStatsService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UnhealthyPipelinesReporter {

    private final PipelineWithStatsService pipelineWithStatsService;

    @Scheduled(fixedRate = 10_000)
    public void reportUnhealthyPipelines() {
        Set<PipelineWithStats> allPipelinesWithStats = pipelineWithStatsService.getAllPipelinesWithStats();

        long countOfUnhealthyPipelines = allPipelinesWithStats.stream()
                .filter(pipelineWithStats -> Health.UNHEALTHY.equals(pipelineWithStats.health())).count();

        if (countOfUnhealthyPipelines > 0) {
            log.warn("UNHEALTHY PIPELINES DETECTED: [{}] --- TAKE ACTION IMMEDIATELY!", countOfUnhealthyPipelines);
        }
    }

}
