package azmiu.library.service.concurate;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.StudentRepository;
import azmiu.library.model.request.StudentRequest;
import azmiu.library.model.response.StudentResponse;
import azmiu.library.service.abstraction.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.StudentMapper.STUDENT_MAPPER;

@RequiredArgsConstructor
@Service
public class StudentServiceHandler implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public void addStudent(UserEntity userEntity, StudentRequest studentRequest) {
        var student = STUDENT_MAPPER.buildStudentEntity(studentRequest);
        student.setUser(userEntity);
        studentRepository.save(student);
    }

    @Override
    public StudentResponse getStudent(UserEntity userEntity) {
        return STUDENT_MAPPER.buildStudentResponse(userEntity);
    }




}
