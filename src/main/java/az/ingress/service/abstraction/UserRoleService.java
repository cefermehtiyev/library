package az.ingress.service.abstraction;

import az.ingress.dao.entity.UserRoleEntity;
import az.ingress.model.enums.RoleName;

public interface UserRoleService {
    void addRole(RoleName roleName);
    Long getCount();
    UserRoleEntity getUserRole(RoleName roleName);
}
