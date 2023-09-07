package app.piper.piper.pipeline.template;

import java.util.UUID;

public record PipelineTemplateWithId(UUID id) {

    public PipelineTemplate toPipelineTemplate() {
        PipelineTemplate pipelineTemplate = new PipelineTemplate();
        pipelineTemplate.setId(this.id);

        return pipelineTemplate;
    }

}
