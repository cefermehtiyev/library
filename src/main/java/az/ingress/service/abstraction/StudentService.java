package az.ingress.service.abstraction;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.RegistrationRequest;

public interface StudentService {
    void addStudent(UserEntity userEntity,RegistrationRequest request);
    StudentEntity getStudentEntityById(Long id);
    StudentEntity getStudentEntityByFin(String fin);

}
