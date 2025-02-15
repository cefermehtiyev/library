package az.ingress.service.abstraction;

import az.ingress.dao.entity.EmployeeEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.EmployeeRequest;
import az.ingress.model.request.RegistrationRequest;

public interface EmployeeService {
    void addEmployee(UserEntity userEntity, EmployeeRequest request);
    EmployeeEntity getEmployeeEntity(String fin);
}
