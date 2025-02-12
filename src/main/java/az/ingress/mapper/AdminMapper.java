package az.ingress.mapper;

import az.ingress.dao.entity.AdminEntity;
import az.ingress.model.request.AdminRequest;

public enum AdminMapper {
    ADMIN_MAPPER;

    public AdminEntity buildAdminEntity(AdminRequest adminRequest){
        return AdminEntity.builder()
                .adminRole(adminRequest.getAdminRole())
                .build();
    }
}
