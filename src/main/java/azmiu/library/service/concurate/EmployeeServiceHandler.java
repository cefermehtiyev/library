package azmiu.library.service.concurate;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.EmployeeRepository;
import azmiu.library.model.request.EmployeeRequest;
import azmiu.library.service.abstraction.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.EmployeeMapper.EMPLOYEE_MAPPER;

@Service
@RequiredArgsConstructor
public class EmployeeServiceHandler implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(UserEntity userEntity, EmployeeRequest request) {
        var employee = EMPLOYEE_MAPPER.buildEmployeeEntity(request);
        employee.setUser(userEntity);
        employeeRepository.save(employee);
    }





}
