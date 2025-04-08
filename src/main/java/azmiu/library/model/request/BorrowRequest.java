package azmiu.library.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static azmiu.library.model.constants.ValidationMessages.FIELD_CANNOT_BE_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String userName;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String bookCode;
}

