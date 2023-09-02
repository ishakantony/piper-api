package app.piper.piper.pipeline.instance;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface PipelineInstanceRepository extends CrudRepository<PipelineInstance, UUID> {

}
