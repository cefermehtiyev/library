package azmiu.library.service.strategy;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.AdminRequest;
import azmiu.library.model.request.EmployeeRequest;
import azmiu.library.model.request.RegistrationRequest;
import azmiu.library.model.request.StudentRequest;
import azmiu.library.service.abstraction.AdminService;
import azmiu.library.service.abstraction.EmployeeService;
import azmiu.library.service.abstraction.StudentService;
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
