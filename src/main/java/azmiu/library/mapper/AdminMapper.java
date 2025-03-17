package azmiu.library.mapper;

import azmiu.library.dao.entity.AdminEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.AdminRequest;
import azmiu.library.model.response.AdminResponse;

public enum AdminMapper {
    ADMIN_MAPPER;

    public AdminEntity buildAdminEntity(AdminRequest adminRequest){
        return AdminEntity.builder()
                .adminRole(adminRequest.getAdminRole())
                .build();
    }

    public AdminResponse buildAdminResponse(UserEntity userEntity){
        return AdminResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .fin(userEntity.getFin())
                .adminRole(userEntity.getAdmin().getAdminRole())
                .createdAt(userEntity.getCreatedAt())
                .build();

    }
}
