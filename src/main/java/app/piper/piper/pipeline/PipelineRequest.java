package app.piper.piper.pipeline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PipelineRequest {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\[\\]() ]+$",
            message = "Name must contain only alphanumeric characters, spaces, [], and ()")
    @Size(max = 255, message = "Name must not exceed {max} characters")
    private String name;

    @Size(max = 4000, message = "Description must not exceed {max} characters")
    private String description;

}
