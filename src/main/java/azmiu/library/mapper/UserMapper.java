package azmiu.library.mapper;

import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.entity.UserRoleEntity;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.enums.RoleName;
import azmiu.library.model.request.RegistrationRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserIdResponse;
import azmiu.library.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collections;

import static azmiu.library.mapper.AdminMapper.ADMIN_MAPPER;
import static azmiu.library.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static azmiu.library.mapper.StudentMapper.STUDENT_MAPPER;

@RequiredArgsConstructor
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
        System.out.println(userEntity.getUserRole().getRoleName());

         switch (userEntity.getUserRole().getRoleName()){
            case  STUDENT ->{
                return STUDENT_MAPPER.buildStudentResponse(userEntity.getStudent());
            }
            case EMPLOYEE -> {
                return EMPLOYEE_MAPPER.buildEmployeeResponse(userEntity.getEmployee());
            }
            case ADMIN -> {
                return
                ADMIN_MAPPER.buildAdminResponse(userEntity);
            }case SUPER_ADMIN ->
             {
                 return ADMIN_MAPPER.buildSuperAdminResponse(userEntity);
             }
            default -> throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }


    public UserIdResponse buildUserIdResponse(UserEntity userEntity){
        return UserIdResponse.builder().id(Long.toString(userEntity.getId())).build();
    }

    public void updateUser(UserEntity userEntity, RegistrationRequest registrationRequest) {
        userEntity.setUserName(registrationRequest.getUserName());
        userEntity.setEmail(registrationRequest.getEmail());
        userEntity.setPassword(registrationRequest.getPassword());
    }

    public PageableResponse<UserResponse> pageableUserResponse(Page<UserEntity> userEntityPage){
        return PageableResponse.<UserResponse>builder()
                .list(userEntityPage.map(this::buildUserResponse).toList())
                .currentPageNumber(userEntityPage.getNumber())
                .totalPages(userEntityPage.getTotalPages())
                .totalElements(userEntityPage.getTotalElements())
                .numberOfElements(userEntityPage.getNumberOfElements())
                .hasNextPage(userEntityPage.hasNext())
                .build();
    }

}
