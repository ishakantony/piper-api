package app.piper.piper.pipeline.instance;

import app.piper.piper.pipeline.InvalidPipelineException;
import app.piper.piper.pipeline.Pipeline;
import app.piper.piper.pipeline.PipelineRepository;
import app.piper.piper.pipeline.template.PipelineTemplate;
import app.piper.piper.pipeline.template.PipelineTemplateRepository;
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

    private final PipelineTemplateRepository pipelineTemplateRepository;

    public List<PipelineInstanceResponse> findByPipelineId(@NonNull UUID pipelineId) {
        Pipeline pipeline = pipelineRepository.findById(pipelineId).orElseThrow(InvalidPipelineException::new);

        return pipelineInstanceRepository.findByPipeline_Id(pipeline.getId()).stream()
                .map(pipelineInstanceMapper::pipelineInstanceToPipelineInstanceResponse).collect(Collectors.toList());
    }

    public PipelineInstanceResponse createFromPipeline(@NonNull UUID pipelineId) {
        Pipeline pipeline = pipelineRepository.findById(pipelineId).orElseThrow(InvalidPipelineException::new);

        PipelineTemplate pipelineTemplate = pipelineTemplateRepository.findByPipelineOrderByRevisionDesc(pipeline)
                .orElseThrow(PipelineTemplateRequiredException::new);

        int currentMaxInstanceNumber = pipelineInstanceRepository.findLatestInstanceNumberByPipeline(pipeline)
                .orElse(0);

        int newInstanceNumber = currentMaxInstanceNumber + 1;

        PipelineInstance newPipelineInstance = new PipelineInstance();

        newPipelineInstance.setName(String.format("%s #%s", pipeline.getName(), newInstanceNumber));
        newPipelineInstance.setInstanceNumber(newInstanceNumber);
        newPipelineInstance.setStatus(PipelineInstanceStatus.NEW);
        newPipelineInstance.setPipeline(pipeline);
        newPipelineInstance.setPipelineTemplate(pipelineTemplate);

        return pipelineInstanceMapper
                .pipelineInstanceToPipelineInstanceResponse(pipelineInstanceRepository.save(newPipelineInstance));
    }

}
