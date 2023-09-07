package app.piper.piper.pipeline;

import app.piper.piper.common.BaseEntity;
import app.piper.piper.pipeline.instance.PipelineInstance;
import app.piper.piper.pipeline.stats.PipelineStats;
import app.piper.piper.pipeline.template.PipelineTemplate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pipeline extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String slug;

    @Column(length = 4000)
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pipeline_id")
    @OrderBy("instanceNumber ASC")
    private Set<PipelineInstance> instances = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pipeline_id")
    @OrderBy("revision ASC")
    private Set<PipelineTemplate> templates = new LinkedHashSet<>();

    @OneToOne(mappedBy = "pipeline", fetch = FetchType.LAZY)
    private PipelineStats stats;

}
