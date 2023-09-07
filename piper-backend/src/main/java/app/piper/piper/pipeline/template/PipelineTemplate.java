package app.piper.piper.pipeline.template;

import app.piper.piper.common.BaseEntity;
import app.piper.piper.pipeline.Pipeline;
import app.piper.piper.pipeline.instance.PipelineInstance;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PipelineTemplate extends BaseEntity {

    @Column(nullable = false)
    private Integer revision;

    @Convert(converter = PipelineTemplateConfigConverter.class)
    @Column(nullable = false)
    private PipelineTemplateConfig config;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pipeline_template_id")
    @OrderBy("instanceNumber ASC")
    private Set<PipelineInstance> instances = new LinkedHashSet<>();

    @ManyToOne
    private Pipeline pipeline;

}
