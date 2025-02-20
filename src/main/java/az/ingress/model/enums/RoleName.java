package az.ingress.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoleName {
    STUDENT, AUTHOR, STAFF, ADMIN, SUPER_ADMIN, EMPLOYEE;

}
