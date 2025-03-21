package azmiu.library.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.concurrent.atomic.LongAccumulator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDetailsResponse {
    private Long bookInventoryId;
    private Integer voteCount;
    private BigDecimal averageRating;
}
