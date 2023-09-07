package app.piper.piper.pipeline;

import app.piper.piper.common.Health;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PipelineWithStats(UUID id, String name, Health health, OffsetDateTime lastSuccessPipelineCompletedAt,
        OffsetDateTime lastFailedPipelineCompletedAt, Integer totalPipelinesCount, Duration longestRunningDuration,
        Duration shortestRunningDuration, Duration averageRunningDuration) {
}
