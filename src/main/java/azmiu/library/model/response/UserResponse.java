package azmiu.library.model.response;

import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private CommonStatus status;
    private RoleName roleName;
    private LocalDateTime createdAt;
}
