package az.ingress.dao.entity;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

public class UserEntity {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role; // MEMBER, STAFF, ADMIN
    @CreationTimestamp
    private LocalDate createdAt;
}
