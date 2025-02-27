package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.EmployeeRequest;

public interface EmployeeService {
    void addEmployee(UserEntity userEntity, EmployeeRequest request);
}
