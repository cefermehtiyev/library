package azmiu.library.model.response;

import azmiu.library.model.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private Long id;
    private Long bookInventoryId;
    private Long userId;
    private Integer score;
    private CommonStatus status;
}
