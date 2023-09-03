package app.piper.piper.pipeline;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
