package azmiu.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingCacheDto implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private Integer voteCount;
    private BigDecimal averageRating;
}