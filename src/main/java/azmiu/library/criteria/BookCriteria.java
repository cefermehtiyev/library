package azmiu.library.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookCriteria {
    private String title;
    private String author;
    private String language;
    private Integer publicationYearFrom;
    private Integer publicationYearTo;
    private Integer readCountFrom;
    private Integer readCountTo;


}
