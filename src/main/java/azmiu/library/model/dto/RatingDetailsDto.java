package azmiu.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class RatingDetailsDto {
    private Integer voteCount;
    private BigDecimal averageRating;
}
