package app.piper.piper.pipeline.instance;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipeline-instances/{instanceId}")
@RequiredArgsConstructor
public class PipelineInstanceOneController {

    private final PipelineInstanceService pipelineInstanceService;

    @GetMapping
    public ResponseEntity<PipelineInstanceResponse> getById(@PathVariable UUID instanceId) {
        return ResponseEntity.ok(pipelineInstanceService.getById(instanceId));
    }

}
