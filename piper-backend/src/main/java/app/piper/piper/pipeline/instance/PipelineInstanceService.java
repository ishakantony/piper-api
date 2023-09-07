package app.piper.piper.pipeline.instance;

import app.piper.piper.pipeline.InvalidPipelineException;
import app.piper.piper.pipeline.Pipeline;
import app.piper.piper.pipeline.PipelineRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineInstanceService {

    private final PipelineRepository pipelineRepository;

    private final PipelineInstanceRepository pipelineInstanceRepository;

    private final PipelineInstanceMapper pipelineInstanceMapper;

    public List<PipelineInstanceResponse> findByPipelineId(@NonNull UUID pipelineId) {
        Pipeline pipeline = pipelineRepository.findById(pipelineId).orElseThrow(InvalidPipelineException::new);

        return pipelineInstanceRepository.findByPipeline_Id(pipeline.getId()).stream()
                .map(pipelineInstanceMapper::pipelineInstanceToPipelineInstanceResponse).collect(Collectors.toList());
    }

}
