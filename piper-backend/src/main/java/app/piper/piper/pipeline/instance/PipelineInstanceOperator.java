package app.piper.piper.pipeline.instance;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PipelineInstanceOperator {

    private final PipelineInstanceRepository pipelineInstanceRepository;

    public void start(@NonNull UUID id, @NonNull OffsetDateTime startAt) {
        PipelineInstance pipelineInstance = this.getById(id);

        pipelineInstance.setStatus(PipelineInstanceStatus.RUNNING);
        pipelineInstance.setStartAt(startAt);

        pipelineInstanceRepository.save(pipelineInstance);
    }

    public void markAsSuccess(@NonNull UUID id, @NonNull OffsetDateTime endAt) {
        PipelineInstance pipelineInstance = this.getById(id);

        pipelineInstance.setStatus(PipelineInstanceStatus.SUCCESSFUL);
        pipelineInstance.setEndAt(endAt);

        pipelineInstanceRepository.save(pipelineInstance);
    }

    public void markAsFailed(@NonNull UUID id, @NonNull OffsetDateTime endAt) {
        PipelineInstance pipelineInstance = this.getById(id);

        pipelineInstance.setStatus(PipelineInstanceStatus.FAILED);
        pipelineInstance.setEndAt(endAt);

        pipelineInstanceRepository.save(pipelineInstance);
    }

    private PipelineInstance getById(UUID id) {
        return pipelineInstanceRepository.findById(id).orElseThrow(InvalidPipelineInstanceException::new);
    }

}
