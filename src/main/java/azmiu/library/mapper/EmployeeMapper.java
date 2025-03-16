package azmiu.library.mapper;

import azmiu.library.dao.entity.EmployeeEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.EmployeeRequest;
import azmiu.library.model.response.EmployeeResponse;

public enum EmployeeMapper {
    EMPLOYEE_MAPPER;

    public EmployeeEntity buildEmployeeEntity(EmployeeRequest request){
        return EmployeeEntity.builder()
                .department(request.getDepartment())
                .position(request.getPosition())
                .build();
    }

    public EmployeeResponse buildEmployeeResponse(UserEntity userEntity){
        var employee = userEntity.getEmployee();
        return EmployeeResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .fin(userEntity.getFin())
                .department(employee.getDepartment())
                .position(employee.getPosition())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }
}
