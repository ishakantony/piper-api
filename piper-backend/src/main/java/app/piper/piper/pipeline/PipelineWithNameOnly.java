package app.piper.piper.pipeline;

import java.util.UUID;

public record PipelineWithNameOnly(UUID id, String name) {

    public Pipeline toPipeline() {
        Pipeline pipeline = new Pipeline();
        pipeline.setId(this.id);
        pipeline.setName(this.name);

        return pipeline;
    }

}
