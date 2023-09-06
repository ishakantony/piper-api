package app.piper.piper.pipeline.stats;

import app.piper.piper.common.BaseEntity;
import app.piper.piper.common.Health;
import app.piper.piper.pipeline.Pipeline;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.Duration;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PipelineStats extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Health health;

    private OffsetDateTime lastSuccessPipelineCompletedAt;

    private OffsetDateTime lastFailedPipelineCompletedAt;

    private Integer totalPipelinesCount;

    private Duration longestRunningDuration;

    private Duration shortestRunningDuration;

    private Duration averageRunningDuration;

    @OneToOne
    @JoinColumn(name = "pipeline_id", referencedColumnName = "id")
    private Pipeline pipeline;

}
