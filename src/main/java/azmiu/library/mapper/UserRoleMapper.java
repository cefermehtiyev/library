package azmiu.library.mapper;

import azmiu.library.dao.entity.UserRoleEntity;
import azmiu.library.model.enums.RoleName;

public enum UserRoleMapper {
    USER_ROLE_MAPPER;

    public UserRoleEntity buildUserRoleEntity(RoleName roleName){
        return UserRoleEntity.builder()
                .roleName(roleName)
                .build();
    }
}
