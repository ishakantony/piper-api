package app.piper.piper.pipeline.stats;

import app.piper.piper.pipeline.Pipeline;
import app.piper.piper.pipeline.PipelineRepository;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineStatsBulkService {

    private final PipelineRepository pipelineRepository;

    private final PipelineStatsService pipelineStatsService;

    public void collectStatsForAllPipelines() {

        // Batch it 100 at a time
        AtomicInteger start = new AtomicInteger(0);
        AtomicBoolean done = new AtomicBoolean(false);

        while (!done.get()) {
            Page<Pipeline> pipelines = pipelineRepository.findAll(PageRequest.of(start.getAndIncrement(), 100));

            if (pipelines.getContent().isEmpty()) {
                done.set(true);
                break;
            }

            pipelines.map(Pipeline::getId).forEach(pipelineStatsService::collectStats);
        }
    }

}
