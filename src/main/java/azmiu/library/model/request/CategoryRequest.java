package azmiu.library.model.request;

import azmiu.library.model.enums.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private BookCategory bookCategory;
}
