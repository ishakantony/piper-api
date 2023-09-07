package app.piper.piper.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {

    private List<T> content;

    private Long totalItems;

    private Integer totalPages;

    private Integer pageSize;

    private Integer pageNumber;

    private Boolean isFirstPage;

    private Boolean isLastPage;

}