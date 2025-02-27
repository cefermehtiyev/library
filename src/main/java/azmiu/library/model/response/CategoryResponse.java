package azmiu.library.model.response;

import azmiu.library.model.enums.BookCategory;
import azmiu.library.model.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private BookCategory bookCategory;
    private CommonStatus status;
}
