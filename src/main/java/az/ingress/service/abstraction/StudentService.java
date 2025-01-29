package az.ingress.service.abstraction;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.StudentResponse;

public interface StudentService {
    void addStudent(UserEntity userEntity,RegistrationRequest request);
    StudentEntity getStudentByFin(String fin);

}
