package azmiu.library.mapper;

import azmiu.library.dao.entity.EmployeeEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.EmployeeRequest;
import azmiu.library.model.response.EmployeeResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserResponse;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;

public enum EmployeeMapper {
    EMPLOYEE_MAPPER;

    public EmployeeEntity buildEmployeeEntity(UserEntity userEntity, EmployeeRequest request){
        return EmployeeEntity.builder().user(userEntity)
                .department(request.getDepartment())
                .position(request.getPosition())
                .build();
    }

    public EmployeeResponse buildEmployeeResponse(EmployeeEntity employeeEntity){
        var userEntity = employeeEntity.getUser();
        return EmployeeResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .fin(userEntity.getFin())
                .department(employeeEntity.getDepartment())
                .position(employeeEntity.getPosition())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }

    public PageableResponse<EmployeeResponse> pageableEmployeeResponse(Page<EmployeeEntity> employeeEntityPage){
        return PageableResponse.<EmployeeResponse>builder()
                .list(employeeEntityPage.map(this::buildEmployeeResponse).toList())
                .currentPageNumber(employeeEntityPage.getNumber())
                .totalPages(employeeEntityPage.getTotalPages())
                .totalElements(employeeEntityPage.getTotalElements())
                .numberOfElements(employeeEntityPage.getNumberOfElements())
                .hasNextPage(employeeEntityPage.hasNext())
                .build();
    }
}
