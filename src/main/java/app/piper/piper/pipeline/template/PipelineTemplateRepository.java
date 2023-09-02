package app.piper.piper.pipeline.template;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface PipelineTemplateRepository extends CrudRepository<PipelineTemplate, UUID> {

}
