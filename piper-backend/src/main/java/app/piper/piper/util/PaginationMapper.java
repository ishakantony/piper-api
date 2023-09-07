package app.piper.piper.util;

import app.piper.piper.common.PaginationResponse;
import java.util.List;
import org.springframework.data.domain.Page;

public class PaginationMapper {

    public static <T, U> PaginationResponse<T> map(List<T> content, Page<U> page) {
        return PaginationResponse.<T>builder().content(content).isFirstPage(page.isFirst()).isLastPage(page.isLast())
                .totalItems(page.getTotalElements()).totalPages(page.getTotalPages())
                .pageSize(page.getNumberOfElements()).pageNumber(page.getNumber()).build();
    }

}
