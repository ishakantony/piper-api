package app.piper.piper.pipeline.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PipelineTemplateConfig {

    // The converter for this only support boolean

    public static final PipelineTemplateConfig DEFAULT = PipelineTemplateConfig.builder().build();

    // Allow multiple pipeline to exist at a time
    // (only use this if your pipeline can support parallel executions)
    @Builder.Default
    private boolean isAllowedMultiple = false;

    // Allow overwriting running pipeline when new pipeline is created
    @Builder.Default
    private boolean isAllowedOverwrite = false;

}
