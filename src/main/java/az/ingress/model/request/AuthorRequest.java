package az.ingress.model.request;

import az.ingress.model.enums.AuthorStatus;
import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest {
    private String name;
    private String biography;
    private LocalDate dateOfBirth;
}
