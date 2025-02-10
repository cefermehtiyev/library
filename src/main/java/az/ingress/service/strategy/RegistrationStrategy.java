package az.ingress.service.strategy;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.EmployeeRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.request.StudentRequest;
import az.ingress.service.abstraction.EmployeeService;
import az.ingress.service.abstraction.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationStrategy {
    private final StudentService studentService;
    private final EmployeeService employeeService;

    public void register(UserEntity userEntity,RegistrationRequest request){
        switch (request.getUserRole()){
            case STUDENT -> studentService.addStudent(userEntity,(StudentRequest)request);
            case EMPLOYEE -> employeeService.addEmployee(userEntity,(EmployeeRequest)request);
        }
    }
}
