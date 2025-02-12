package az.ingress.model.request;

import az.ingress.model.enums.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private BookCategory bookCategory;
}
