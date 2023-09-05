package app.piper.piper.pipeline.stats;

import org.mapstruct.Mapper;

@Mapper
public interface PipelineStatsMapper {

    PipelineStatsResponse pipelineStatsToPipelineStatsResponse(PipelineStats pipelineStats);

}
