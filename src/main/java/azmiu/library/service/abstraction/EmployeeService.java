package azmiu.library.service.abstraction;

import azmiu.library.criteria.EmployeeCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.EmployeeRequest;
import azmiu.library.model.response.EmployeeResponse;
import azmiu.library.model.response.PageableResponse;

public interface EmployeeService {
    void addEmployee(UserEntity userEntity, EmployeeRequest request);

    PageableResponse<EmployeeResponse> getAllEmployee(PageCriteria pageCriteria, EmployeeCriteria employeeCriteria);
}
