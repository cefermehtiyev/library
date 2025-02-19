package az.ingress.model.response;

import az.ingress.model.enums.CommonStatus;
import az.ingress.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private CommonStatus status;
    private UserRole userRole;
    private LocalDate createdAt;
}
