package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.StudentRequest;

public interface StudentService {
    void addStudent(UserEntity userEntity, StudentRequest request);



}
