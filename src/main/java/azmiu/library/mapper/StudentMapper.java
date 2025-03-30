package azmiu.library.mapper;

import azmiu.library.dao.entity.StudentEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.StudentRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.StudentResponse;
import azmiu.library.model.response.UserResponse;
import org.springframework.data.domain.Page;

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

    public StudentResponse buildStudentResponsePageable(StudentEntity studentEntity) {
        var userEntity = studentEntity.getUser();
        return StudentResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .status(userEntity.getCommonStatus().getStatus())
                .roleName(userEntity.getUserRole().getRoleName())
                .fin(userEntity.getFin())
                .specialization(studentEntity.getSpecialization())
                .groupName(studentEntity.getGroupName())
                .course(studentEntity.getCourse())
                .createdAt(userEntity.getCreatedAt())
                .build();

    }


    public PageableResponse<StudentResponse> pageableStudentResponse(Page<StudentEntity> userEntityPage){
        return PageableResponse.<StudentResponse>builder()
                .list(userEntityPage.map(this::buildStudentResponsePageable).toList())
                .currentPageNumber(userEntityPage.getNumber())
                .totalPages(userEntityPage.getTotalPages())
                .totalElements(userEntityPage.getTotalElements())
                .numberOfElements(userEntityPage.getNumberOfElements())
                .hasNextPage(userEntityPage.hasNext())
                .build();
    }
}
