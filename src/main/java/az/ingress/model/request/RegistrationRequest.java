package az.ingress.model.request;

import az.ingress.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "userRole",visible = true) // JSON-dakı "userRole" əsasən modeli seçir
@JsonSubTypes({
        @JsonSubTypes.Type(value = StudentRequest.class, name = "STUDENT"),
        @JsonSubTypes.Type(value = EmployeeRequest.class, name = "EMPLOYEE"),
})
public class RegistrationRequest {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String fin;
    private UserRole userRole;
}
