package az.ingress.model.response;

import az.ingress.model.enums.BookCategory;
import az.ingress.model.enums.CategoryStatus;
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
    private CategoryStatus categoryStatus;
}
