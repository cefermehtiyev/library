package az.ingress.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCriteria {
    private String title;
    private String author;
    private Integer publicationYearFrom;
    private Integer publicationYearTo;


}
