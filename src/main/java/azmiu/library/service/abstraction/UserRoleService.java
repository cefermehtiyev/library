package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.UserRoleEntity;
import azmiu.library.model.enums.RoleName;

public interface UserRoleService {
    void addRole(RoleName roleName);
    Long getCount();
    UserRoleEntity getUserRole(RoleName roleName);
}
