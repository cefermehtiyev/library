package azmiu.library.model.request;

import azmiu.library.model.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "roleName", visible = true)
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
    private RoleName roleName;
}
