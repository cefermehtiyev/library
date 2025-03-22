package azmiu.library.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static azmiu.library.model.constants.ValidationMessages.FIELD_CANNOT_BE_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    String bookCode;
}
