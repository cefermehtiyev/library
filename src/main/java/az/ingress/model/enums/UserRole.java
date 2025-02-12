package az.ingress.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRole {
    STUDENT, AUTHOR,STAFF,ADMIN,EMPLOYEE;

}
