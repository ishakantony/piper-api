package app.piper.piper.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaginationRequest {

    @Min(0)
    private Integer pageNumber = 0;

    @Size(min = 1, max = 100)
    private Integer pageSize = 5;

    private String sortBy = "createdAt";

    @Pattern(regexp = "^(ASC|DESC)$", message = "Value must be 'ASC' or 'DESC'")
    private String sortDirection = "DESC";

}
