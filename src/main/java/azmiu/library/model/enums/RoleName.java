package azmiu.library.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoleName {
    STUDENT,
    EMPLOYEE,
    ADMIN,
    SUPER_ADMIN,
}
