package azmiu.library.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static azmiu.library.model.constants.ValidationMessages.FIELD_CANNOT_BE_NULL;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest extends RegistrationRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String specialization;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String group;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private Integer course;


}
