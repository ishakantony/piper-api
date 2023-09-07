package app.piper.piper.pipeline.instance;

import app.piper.piper.common.BaseEntity;
import app.piper.piper.pipeline.Pipeline;
import app.piper.piper.pipeline.template.PipelineTemplate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class PipelineInstance extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer instanceNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PipelineInstanceStatus status = PipelineInstanceStatus.NEW;

    private OffsetDateTime startAt;

    private OffsetDateTime endAt;

    @ManyToOne
    private Pipeline pipeline;

    @ManyToOne
    private PipelineTemplate pipelineTemplate;

}
