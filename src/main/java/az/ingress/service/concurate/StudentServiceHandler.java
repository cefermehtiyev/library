package az.ingress.service.concurate;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.StudentRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.request.StudentRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.StudentResponse;
import az.ingress.service.abstraction.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.mapper.StudentMapper.STUDENT_MAPPER;

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


}
