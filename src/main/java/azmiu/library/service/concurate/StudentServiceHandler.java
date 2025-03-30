package azmiu.library.service.concurate;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.StudentCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.StudentRepository;
import azmiu.library.model.request.StudentRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.StudentResponse;
import azmiu.library.service.abstraction.StudentService;
import azmiu.library.service.specification.StudentSpecification;
import azmiu.library.service.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public PageableResponse<StudentResponse> getAllStudents(PageCriteria pageCriteria, StudentCriteria studentCriteria) {
        var studentPage = studentRepository.findAll(
                new StudentSpecification(studentCriteria),
                PageRequest.of(pageCriteria.getPage(),pageCriteria.getCount())
        );

        return STUDENT_MAPPER.pageableStudentResponse(studentPage);
    }


}
