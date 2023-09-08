package app.piper.piper.pipeline.instance;

import app.piper.piper.pipeline.Pipeline;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineInstanceRepository extends Repository<PipelineInstance, UUID> {

    @Transactional(readOnly = true)
    long count();

    @Transactional(readOnly = true)
    List<PipelineInstance> findByPipeline_Id(UUID pipelineId);

    @Transactional(readOnly = true)
    Page<PipelineInstance> findPageByPipeline_id(UUID pipelineId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT MAX(pi.instanceNumber) FROM PipelineInstance pi WHERE pi.pipeline = :pipeline")
    Optional<Integer> findLatestInstanceNumberByPipeline(Pipeline pipeline);

    @Transactional
    PipelineInstance save(PipelineInstance pipelineInstance);

    @Transactional(readOnly = true)
    Optional<PipelineInstance> findById(UUID id);

}
