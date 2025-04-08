package azmiu.library.model.response;

import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String fin;
    private String email;
    private CommonStatus status;
    private RoleName roleName;
    private LocalDateTime createdAt;
}
