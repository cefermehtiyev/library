package azmiu.library.mapper;

import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.entity.UserRoleEntity;
import azmiu.library.model.request.RegistrationRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserIdResponse;
import azmiu.library.model.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.Collections;

public enum UserMapper {
    USER_MAPPER;

    public UserEntity buildUserEntity(RegistrationRequest registrationRequest, CommonStatusEntity commonStatus, UserRoleEntity userRole) {
        return UserEntity.builder()
                .userName(registrationRequest.getUserName())
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .fin(registrationRequest.getFin())
                .userRole(userRole)
                .commonStatus(commonStatus)
                .build();
    }

    public UserResponse buildUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }

    public UserIdResponse buildUserIdResponse(UserEntity userEntity){
        return UserIdResponse.builder().id(Long.toString(userEntity.getId())).build();
    }

    public void updateUser(UserEntity userEntity, RegistrationRequest registrationRequest) {
        userEntity.setUserName(registrationRequest.getUserName());
        userEntity.setEmail(registrationRequest.getEmail());
        userEntity.setPassword(registrationRequest.getPassword());
    }

    public PageableResponse pageableUserResponse(Page<UserEntity> userEntityPage){
        return PageableResponse.builder()
                .list(Collections.singletonList(userEntityPage.map(this::buildUserResponse).toList()))
                .currentPageNumber(userEntityPage.getNumber())
                .totalPages(userEntityPage.getTotalPages())
                .totalElements(userEntityPage.getTotalElements())
                .numberOfElements(userEntityPage.getNumberOfElements())
                .hasNextPage(userEntityPage.hasNext())
                .build();
    }

}
