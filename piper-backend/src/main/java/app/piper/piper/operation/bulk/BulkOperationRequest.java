package app.piper.piper.operation.bulk;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BulkOperationRequest {

    @NotEmpty
    private String operation;

    private BulkExecutionMode mode = BulkExecutionMode.SYNC;

}
