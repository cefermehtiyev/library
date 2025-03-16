package azmiu.library.mapper;

import azmiu.library.dao.entity.StudentEntity;
import azmiu.library.dao.entity.UserEntity;
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

    public StudentResponse buildStudentResponse(UserEntity userEntity) {
        var student = userEntity.getStudent();
        return StudentResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .fin(userEntity.getFin())
                .specialization(student.getSpecialization())
                .groupName(student.getGroupName())
                .course(student.getCourse())
                .createdAt(userEntity.getCreatedAt())
                .build();

    }
}
