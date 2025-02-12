package az.ingress.mapper;

import az.ingress.dao.entity.EmployeeEntity;
import az.ingress.model.request.EmployeeRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.EmployeeResponse;

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
