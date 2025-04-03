package azmiu.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDetailsDto {
    private Long bookInventoryId;
    private Integer voteCount;
    private BigDecimal averageRating;
}
