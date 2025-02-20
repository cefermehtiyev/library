package az.ingress.mapper;

import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.model.enums.RoleName;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.PageableResponse;
import az.ingress.model.response.UserIdResponse;
import az.ingress.model.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.Collections;

public enum UserMapper {
    USER_MAPPER;

    public UserEntity buildUserEntity(RegistrationRequest registrationRequest, CommonStatusEntity commonStatus) {
        return UserEntity.builder()
                .userName(registrationRequest.getUserName())
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .fin(registrationRequest.getFin())
                .commonStatus(commonStatus)
                .build();
    }

    public UserResponse buildUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(RoleName.STUDENT)
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
