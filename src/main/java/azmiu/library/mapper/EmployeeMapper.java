package azmiu.library.mapper;

import azmiu.library.dao.entity.EmployeeEntity;
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

    public EmployeeResponse buildEmployeeResponse(EmployeeEntity employeeEntity){
        return EmployeeResponse.builder()
                .id(employeeEntity.getId())

                .department(employeeEntity.getDepartment())
                .position(employeeEntity.getPosition())
                .build();
    }
}
