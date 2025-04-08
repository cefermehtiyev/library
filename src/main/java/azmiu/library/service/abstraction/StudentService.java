package azmiu.library.service.abstraction;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.StudentCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.StudentRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.StudentResponse;

public interface StudentService {
    void addStudent(UserEntity userEntity, StudentRequest request);
    StudentResponse getStudent(Long id);
    PageableResponse<StudentResponse> getAllStudents(PageCriteria pageCriteria, StudentCriteria studentCriteria);



}
