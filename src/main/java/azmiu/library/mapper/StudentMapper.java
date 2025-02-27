package azmiu.library.mapper;

import azmiu.library.dao.entity.StudentEntity;
import azmiu.library.model.request.StudentRequest;
import azmiu.library.model.response.StudentResponse;

public enum StudentMapper {
    STUDENT_MAPPER;

    public StudentEntity buildStudentEntity(StudentRequest request) {
        return StudentEntity.builder()
                .specialization(request.getSpecialization())
                .course(request.getCourse())
                .groupName(request.getGroup())
                .build();

    }

    public StudentResponse buildStudentResponse(StudentEntity studentEntity) {
        return StudentResponse.builder()
                .id(studentEntity.getId()).build();

    }
}
