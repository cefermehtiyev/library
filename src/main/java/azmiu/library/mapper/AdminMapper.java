package azmiu.library.mapper;

import azmiu.library.dao.entity.AdminEntity;
import azmiu.library.model.request.AdminRequest;

public enum AdminMapper {
    ADMIN_MAPPER;

    public AdminEntity buildAdminEntity(AdminRequest adminRequest){
        return AdminEntity.builder()
                .adminRole(adminRequest.getAdminRole())
                .build();
    }
}
