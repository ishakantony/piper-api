package app.piper.piper.pipeline.stats;

import java.util.UUID;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineStatsRepository extends Repository<PipelineStats, UUID> {

    @Transactional
    PipelineStats save(PipelineStats pipelineStats);

}
