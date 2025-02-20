package az.ingress.service.strategy;

import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.AdminRequest;
import az.ingress.model.request.EmployeeRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.request.StudentRequest;
import az.ingress.service.abstraction.AdminService;
import az.ingress.service.abstraction.EmployeeService;
import az.ingress.service.abstraction.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationStrategy {
    private final StudentService studentService;
    private final EmployeeService employeeService;
    private final AdminService adminService;

    public void register(UserEntity userEntity, RegistrationRequest request) {
        switch (request.getRoleName()) {
            case STUDENT -> studentService.addStudent(userEntity, (StudentRequest) request);
            case EMPLOYEE -> employeeService.addEmployee(userEntity, (EmployeeRequest) request);
            case ADMIN -> adminService.addAdmin(userEntity, (AdminRequest) request);
        }
    }
}
