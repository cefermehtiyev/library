package azmiu.library.model.dto;

import azmiu.library.model.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {
    private Long userId;
    private Long bookInventoryId;
    private Integer score;
    private CommonStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}