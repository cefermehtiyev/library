package azmiu.library.model.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static azmiu.library.model.constants.ValidationMessages.FIELD_CANNOT_BE_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String name;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String biography;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private LocalDate dateOfBirth;
}
