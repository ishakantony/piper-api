package app.piper.piper.pipeline.stats;

import app.piper.piper.pipeline.Pipeline;
import app.piper.piper.pipeline.PipelineNotFoundException;
import app.piper.piper.pipeline.PipelineRepository;
import app.piper.piper.pipeline.instance.PipelineInstance;
import app.piper.piper.pipeline.instance.PipelineInstanceRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineStatsService {

    private final PipelineRepository pipelineRepository;

    private final PipelineInstanceRepository pipelineInstanceRepository;

    private final PipelineStatsRepository pipelineStatsRepository;

    private final PipelineStatsMapper pipelineStatsMapper;

    private final PipelineStatsCollector pipelineStatsCollector;

    public PipelineStatsResponse collectStats(@NonNull UUID pipelineId) {

        // We can only collect stats for valid pipeline
        Pipeline pipeline = pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotFoundException::new);

        // Create new stats if existing one don't have
        PipelineStats stats = Optional.ofNullable(pipeline.getStats()).orElse(new PipelineStats());

        // We collect stats from completed instances, doesn't matter from which template
        // Completed instances means that it's not NEW and not RUNNING
        List<PipelineInstance> instances = pipelineInstanceRepository.findByPipeline_Id(pipelineId);

        if (instances.isEmpty()) {
            // There no instances for this pipeline, cannot collect stats
            return pipelineStatsMapper.pipelineStatsToPipelineStatsResponse(pipelineStatsRepository.save(stats));
        }
        else {
            return pipelineStatsMapper.pipelineStatsToPipelineStatsResponse(
                    pipelineStatsRepository.save(pipelineStatsCollector.collectStatsFromPipelineInstances(instances)));
        }
    }

}
