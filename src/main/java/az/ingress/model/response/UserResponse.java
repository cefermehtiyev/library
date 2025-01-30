package az.ingress.model.response;

import az.ingress.model.enums.UserRole;
import az.ingress.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserStatus userStatus;
    private UserRole userRole;
    private LocalDate createdAt;
}
