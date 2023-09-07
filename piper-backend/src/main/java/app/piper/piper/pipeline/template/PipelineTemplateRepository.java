package app.piper.piper.pipeline.template;

import app.piper.piper.pipeline.Pipeline;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineTemplateRepository extends Repository<PipelineTemplate, UUID> {

    @Transactional(readOnly = true)
    long count();

    @Query("SELECT new app.piper.piper.pipeline.template.PipelineTemplateWithId(pt.id) FROM PipelineTemplate pt WHERE pt.pipeline = :pipeline ORDER BY pt.revision DESC LIMIT 1")
    Optional<PipelineTemplateWithId> findLatestByPipeline(Pipeline pipeline);

}
