package app.piper.piper.operation.action;

import app.piper.piper.pipeline.instance.PipelineInstanceOperator;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_action/pipeline-instances/{instanceId}")
@RequiredArgsConstructor
public class PipelineInstanceActionController {

    private final PipelineInstanceOperator operator;

    @PostMapping("/start")
    public void startInstance(@PathVariable UUID instanceId,
            @Valid @RequestBody PipelineInstanceActionRequest request) {
        operator.start(instanceId, request.getTime());
    }

    @PostMapping("/mark-as-success")
    public void markInstanceAsSuccess(@PathVariable UUID instanceId,
            @Valid @RequestBody PipelineInstanceActionRequest request) {
        operator.markAsSuccess(instanceId, request.getTime());
    }

    @PostMapping("/mark-as-failed")
    public void markInstanceAsFailed(@PathVariable UUID instanceId,
            @Valid @RequestBody PipelineInstanceActionRequest request) {
        operator.markAsFailed(instanceId, request.getTime());
    }

}
