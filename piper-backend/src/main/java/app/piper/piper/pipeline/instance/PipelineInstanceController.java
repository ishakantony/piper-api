package app.piper.piper.pipeline.instance;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipelines/{pipelineId}/instances")
@RequiredArgsConstructor
public class PipelineInstanceController {

    private final PipelineInstanceService pipelineInstanceService;

    @GetMapping
    public ResponseEntity<List<PipelineInstanceResponse>> getByPipelineId(@PathVariable UUID pipelineId) {
        return ResponseEntity.ok(pipelineInstanceService.findByPipelineId(pipelineId));
    }

    @PostMapping
    public ResponseEntity<PipelineInstanceResponse> createNewInstance(@PathVariable UUID pipelineId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pipelineInstanceService.createFromPipeline(pipelineId));
    }

}
