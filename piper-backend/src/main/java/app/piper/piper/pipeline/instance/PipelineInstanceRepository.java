package app.piper.piper.pipeline.instance;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineInstanceRepository extends Repository<PipelineInstance, UUID> {

    @Transactional(readOnly = true)
    long count();

    @Transactional(readOnly = true)
    List<PipelineInstance> findByPipeline_Id(UUID pipelineId);

}
