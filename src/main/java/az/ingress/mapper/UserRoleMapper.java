package az.ingress.mapper;

import az.ingress.dao.entity.UserRoleEntity;
import az.ingress.model.enums.RoleName;

public enum UserRoleMapper {
    USER_ROLE_MAPPER;

    public UserRoleEntity buildUserRoleEntity(RoleName roleName){
        return UserRoleEntity.builder()
                .roleName(roleName)
                .build();
    }
}
