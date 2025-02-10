package az.ingress.mapper;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.model.request.StudentRequest;
import az.ingress.model.response.StudentResponse;

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
