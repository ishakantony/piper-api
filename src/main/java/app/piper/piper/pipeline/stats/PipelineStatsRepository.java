package app.piper.piper.pipeline.stats;

import app.piper.piper.pipeline.PipelineWithStats;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineStatsRepository extends Repository<PipelineStats, UUID> {

    @Query("SELECT new app.piper.piper.pipeline.PipelineWithStats(ps.pipeline.id, ps.pipeline.name, ps.health, ps.lastSuccessPipelineCompletedAt, ps.lastFailedPipelineCompletedAt, ps.totalPipelinesCount, ps.longestRunningDuration, ps.shortestRunningDuration, ps.averageRunningDuration) FROM PipelineStats ps")
    Set<PipelineWithStats> findAllWithStats();

    @Transactional
    PipelineStats save(PipelineStats pipelineStats);

}
