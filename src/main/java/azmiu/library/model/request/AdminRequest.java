package azmiu.library.model.request;

import azmiu.library.model.enums.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest extends RegistrationRequest {
    private AdminRole adminRole;
}
