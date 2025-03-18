package azmiu.library.model.request;

import azmiu.library.model.constants.ValidationMessages;
import azmiu.library.model.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static azmiu.library.model.constants.ValidationMessages.FIELD_CANNOT_BE_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "roleName", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StudentRequest.class, name = "STUDENT"),
        @JsonSubTypes.Type(value = EmployeeRequest.class, name = "EMPLOYEE"),
        @JsonSubTypes.Type(value = AdminRequest.class, name = "ADMIN")
})
public class RegistrationRequest {
    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String userName;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String email;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String password;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String firstName;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String lastName;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private String fin;

    @NotNull(message = FIELD_CANNOT_BE_NULL)
    private RoleName roleName;
}
