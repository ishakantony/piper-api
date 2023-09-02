package app.piper.piper.pipeline;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface PipelineRepository extends CrudRepository<Pipeline, UUID> {

}
