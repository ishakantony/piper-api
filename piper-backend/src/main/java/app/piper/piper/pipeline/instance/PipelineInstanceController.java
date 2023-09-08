package app.piper.piper.pipeline.instance;

import app.piper.piper.common.PaginationRequest;
import app.piper.piper.common.PaginationResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity<PaginationResponse<PipelineInstanceResponse>> getByPipelineId(@PathVariable UUID pipelineId,
            @ModelAttribute PaginationRequest paginationRequest) {
        return ResponseEntity.ok(pipelineInstanceService.findByPipelineId(pipelineId, paginationRequest));
    }

    @PostMapping
    public ResponseEntity<PipelineInstanceResponse> createNewInstance(@PathVariable UUID pipelineId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pipelineInstanceService.createFromPipeline(pipelineId));
    }

}
