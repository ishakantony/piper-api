package app.piper.piper.pipeline;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PipelineRepository extends Repository<Pipeline, UUID> {

    @Transactional(readOnly = true)
    Optional<Pipeline> findById(UUID id);

    @Transactional(readOnly = true)
    Page<Pipeline> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    long count();

    @Transactional
    Pipeline save(Pipeline pipeline);

}
