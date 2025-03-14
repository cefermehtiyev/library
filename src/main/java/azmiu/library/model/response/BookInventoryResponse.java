package azmiu.library.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookInventoryResponse {
    private String title;
    private Integer publicationYear;
    private Integer reservedQuantity;
    private Integer borrowedQuantity;
    private Integer availableQuantity;
    private Long readCount;
}
