package az.ingress.mapper;

import az.ingress.dao.entity.StudentEntity;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.StudentResponse;

public enum StudentMapper {
    STUDENT_MAPPER;

    public StudentEntity buildStudentEntity(RegistrationRequest request) {
        return StudentEntity
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fin(request.getFin())
                .build();

    }

    public StudentResponse buildStudentResponse(StudentEntity studentEntity) {
        return StudentResponse
                .builder()
                .id(studentEntity.getId())
                .firstName(studentEntity.getFirstName())
                .lastName(studentEntity.getLastName())
                .fin(studentEntity.getFin()).build();
    }
}
