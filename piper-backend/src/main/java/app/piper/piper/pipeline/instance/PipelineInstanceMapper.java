package app.piper.piper.pipeline.instance;

import org.mapstruct.Mapper;

@Mapper
public interface PipelineInstanceMapper {

    PipelineInstanceResponse pipelineInstanceToPipelineInstanceResponse(PipelineInstance pipelineInstance);

}
