package az.ingress.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T>{
    private List<T> users;
    private Integer lastPageNumber;
    private Integer totalPageNumber;
    private boolean hasNextPage;

}
