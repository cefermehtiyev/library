package az.ingress.mapper;

import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.PageableResponse;
import az.ingress.model.response.UserIdResponse;
import az.ingress.model.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.Collections;

import static az.ingress.model.enums.UserRole.STUDENT;
import static az.ingress.model.enums.UserStatus.ACTIVE;

public enum UserMapper {
    USER_MAPPER;

    public UserEntity buildUserEntity(RegistrationRequest registrationRequest) {
        return UserEntity.builder()
                .userName(registrationRequest.getUserName())
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .userStatus(ACTIVE)
                .build();
    }

    public UserResponse buildUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getUserName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .userStatus(userEntity.getUserStatus())
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
                .users(Collections.singletonList(userEntityPage.map(this::buildUserResponse).toList()))
                .lastPageNumber(userEntityPage.getNumber())
                .totalPageNumber(userEntityPage.getTotalPages())
                .hasNextPage(userEntityPage.hasNext())
                .build();
    }

}
