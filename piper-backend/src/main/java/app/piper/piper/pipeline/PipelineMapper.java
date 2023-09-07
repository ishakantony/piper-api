package app.piper.piper.pipeline;

import org.mapstruct.Mapper;

@Mapper
public interface PipelineMapper {

    Pipeline pipelineRequestToPipeline(PipelineRequest pipelineRequest);

    PipelineResponse pipelineToPipelineResponse(Pipeline pipeline);

}
