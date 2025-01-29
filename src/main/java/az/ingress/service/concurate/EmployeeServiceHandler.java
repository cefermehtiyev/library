package az.ingress.service.concurate;

import az.ingress.dao.entity.EmployeeEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.EmployeeRepository;
import az.ingress.mapper.EmployeeMapper;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.service.abstraction.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.EmployeeMapper.EMPLOYEE_MAPPER;

@Service
@RequiredArgsConstructor
public class EmployeeServiceHandler implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(UserEntity userEntity, RegistrationRequest request) {
        var employee = EMPLOYEE_MAPPER.buildEmployeeEntity(request);
        employee.setUser(userEntity);
        employeeRepository.save(employee);
    }
}
