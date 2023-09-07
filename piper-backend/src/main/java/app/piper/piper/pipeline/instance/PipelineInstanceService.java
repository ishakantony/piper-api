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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.debug("Creating pipeline instance for pipeline id [{}]", pipelineId);

        Pipeline pipeline = pipelineRepository.findWithNameOnlyById(pipelineId)
                .orElseThrow(InvalidPipelineException::new).toPipeline();
        log.debug("Pipeline found [{}]", pipeline);

        PipelineTemplate pipelineTemplate = pipelineTemplateRepository.findLatestByPipeline(pipeline)
                .orElseThrow(PipelineTemplateRequiredException::new).toPipelineTemplate();
        log.debug("Pipeline template found [{}]", pipelineTemplate);

        int currentMaxInstanceNumber = pipelineInstanceRepository.findLatestInstanceNumberByPipeline(pipeline)
                .orElse(0);

        int newInstanceNumber = currentMaxInstanceNumber + 1;
        log.debug("Current max instance number is [{}], next is [{}]", currentMaxInstanceNumber, newInstanceNumber);

        PipelineInstance newPipelineInstance = new PipelineInstance();

        newPipelineInstance.setName(String.format("%s #%s", pipeline.getName(), newInstanceNumber));
        newPipelineInstance.setInstanceNumber(newInstanceNumber);
        newPipelineInstance.setStatus(PipelineInstanceStatus.NEW);
        newPipelineInstance.setPipeline(pipeline);
        newPipelineInstance.setPipelineTemplate(pipelineTemplate);

        log.debug("Creating new pipeline instance [{}]", newPipelineInstance);
        return pipelineInstanceMapper
                .pipelineInstanceToPipelineInstanceResponse(pipelineInstanceRepository.save(newPipelineInstance));
    }

}
