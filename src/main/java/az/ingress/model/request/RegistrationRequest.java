package az.ingress.model.request;

import az.ingress.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String fin;
    private String department;
    private String position;
    private UserRole userRole;
}
