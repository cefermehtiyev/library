package azmiu.library.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static azmiu.library.model.constants.ValidationMessages.FIELD_CANNOT_BE_NULL;
import static azmiu.library.model.constants.ValidationMessages.RATING_MAX_VALUE;
import static azmiu.library.model.constants.ValidationMessages.RATING_MIN_VALUE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private Long userId;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private Long bookInventoryId;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    @Min(value = 1, message = RATING_MIN_VALUE)
    @Max(value = 5, message = RATING_MAX_VALUE)
    private Integer score;

}
