package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.StudentRequest;
import azmiu.library.model.response.StudentResponse;

public interface StudentService {
    void addStudent(UserEntity userEntity, StudentRequest request);
    StudentResponse getStudent(UserEntity userEntity);



}
