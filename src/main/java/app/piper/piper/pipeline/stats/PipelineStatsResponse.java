package app.piper.piper.pipeline.stats;

import app.piper.piper.common.Health;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Duration;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class PipelineStatsResponse {

    private Health health;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastSuccessPipelineCompletedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastFailedPipelineCompletedAt;

    private Integer totalPipelinesCount;

    private Duration longestRunningDuration;

    private Duration shortestRunningDuration;

    private Duration averageRunningDuration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

}
