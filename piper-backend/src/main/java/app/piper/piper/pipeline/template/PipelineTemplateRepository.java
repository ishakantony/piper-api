package app.piper.piper.pipeline.template;

import app.piper.piper.pipeline.Pipeline;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineTemplateRepository extends Repository<PipelineTemplate, UUID> {

    @Transactional(readOnly = true)
    long count();

    @Transactional(readOnly = true)
    Optional<PipelineTemplate> findByPipelineOrderByRevisionDesc(Pipeline pipeline);

}
