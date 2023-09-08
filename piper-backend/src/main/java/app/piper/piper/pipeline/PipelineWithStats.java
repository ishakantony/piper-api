package app.piper.piper.pipeline;

import app.piper.piper.common.Health;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PipelineWithStats(UUID id, String name, Health health,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") OffsetDateTime lastSuccessPipelineCompletedAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") OffsetDateTime lastFailedPipelineCompletedAt,
        Integer totalPipelinesCount, Duration longestRunningDuration, Duration shortestRunningDuration,
        Duration averageRunningDuration) {
}
