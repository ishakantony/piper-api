package app.piper.piper.pipeline;

import app.piper.piper.pipeline.stats.PipelineStatsRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineWithStatsService {

    private final PipelineStatsRepository pipelineStatsRepository;

    public Set<PipelineWithStats> getAllPipelinesWithStats() {
        return pipelineStatsRepository.findAllWithStats();
    }

}
