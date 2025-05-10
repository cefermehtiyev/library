package azmiu.library.mapper;

import azmiu.library.dao.entity.AdminEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.AdminRequest;
import azmiu.library.model.response.AdminResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.data.domain.Page;

public enum AdminMapper {
    ADMIN_MAPPER;

    public AdminEntity buildAdminEntity(UserEntity userEntity, AdminRequest adminRequest) {
        return AdminEntity
                .builder()
                .user(userEntity)
                .adminRole(adminRequest.getAdminRole())
                .build();
    }


    public AdminResponse buildSuperAdminResponse(UserEntity userEntity) {
        return AdminResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .fin(userEntity.getFin())
                .createdAt(userEntity.getCreatedAt())
                .build();

    }

    public AdminResponse buildAdminResponse(AdminEntity adminEntity) {
        var userEntity = adminEntity.getUser();
        return AdminResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .fin(userEntity.getFin())
                .adminRole(adminEntity.getAdminRole())
                .createdAt(userEntity.getCreatedAt())
                .build();

    }


    public PageableResponse<AdminResponse> pageableStudentResponse(Page<AdminEntity> adminEntityPage) {
        return PageableResponse.<AdminResponse>builder()
                .list(adminEntityPage.map(this::buildAdminResponse).toList())
                .currentPageNumber(adminEntityPage.getNumber())
                .totalPages(adminEntityPage.getTotalPages())
                .totalElements(adminEntityPage.getTotalElements())
                .numberOfElements(adminEntityPage.getNumberOfElements())
                .hasNextPage(adminEntityPage.hasNext())
                .build();
    }
}
