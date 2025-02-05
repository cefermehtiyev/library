package az.ingress.mapper;

import az.ingress.dao.entity.EmployeeEntity;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.EmployeeResponse;

public enum EmployeeMapper {
    EMPLOYEE_MAPPER;

    public EmployeeEntity buildEmployeeEntity(RegistrationRequest request){
        return EmployeeEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fin(request.getFin())
                .department(request.getDepartment())
                .position(request.getPosition())
                .build();
    }

    public EmployeeResponse buildEmployeeResponse(EmployeeEntity employeeEntity){
        return EmployeeResponse.builder()
                .id(employeeEntity.getId())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .fin(employeeEntity.getFin())
                .department(employeeEntity.getDepartment())
                .position(employeeEntity.getPosition())
                .build();
    }
}
