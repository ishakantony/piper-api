package app.piper.piper.pipeline;

import app.piper.piper.common.PaginationRequest;
import app.piper.piper.common.PaginationResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipelines")
@RequiredArgsConstructor
public class PipelineController {

    private final PipelineService pipelineService;

    @PostMapping
    public ResponseEntity<PipelineResponse> createPipeline(@Valid @RequestBody PipelineRequest pipelineRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pipelineService.createPipeline(pipelineRequest));
    }

    @GetMapping
    public PaginationResponse<PipelineResponse> getPipelines(@ModelAttribute PaginationRequest paginationRequest) {
        return pipelineService.getPipelines(paginationRequest);
    }

    @GetMapping("/{pipelineId}")
    public PipelineResponse getPipelineById(@PathVariable UUID pipelineId) {
        return pipelineService.findById(pipelineId);
    }

}
