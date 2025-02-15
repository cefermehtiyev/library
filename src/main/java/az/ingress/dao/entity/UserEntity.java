package az.ingress.dao.entity;

import az.ingress.model.enums.UserRole;
import az.ingress.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Where(clause = "book_status <> 'DELETED'")
@Builder
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String userName;
    private String email;
    private String password;
    @Enumerated(STRING)
    private UserRole userRole;
    private String firstName;
    private String lastName;
    private String fin;
    @Enumerated(STRING)
    private UserStatus userStatus;
    @CreationTimestamp
    private LocalDate createdAt;

    @OneToOne(
            mappedBy = "user",
            cascade = {PERSIST,MERGE}
    )
    @ToString.Exclude
    EmployeeEntity employee;

    @OneToOne(
            mappedBy = "user",
            cascade = {PERSIST,MERGE}
    )
    @ToString.Exclude
    AdminEntity admin;

    @OneToOne(
            mappedBy = "user",
            cascade = {PERSIST,MERGE}
    )
    @ToString.Exclude
    StudentEntity student;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE,PERSIST},
            mappedBy = "user"
    )
    @ToString.Exclude
    List<BookBorrowHistoryEntity> bookBorrowHistoryEntity;

    @ManyToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinTable(
            name = "book_borrow_history",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @ToString.Exclude
    @JsonBackReference
    List<BookEntity> bookEntities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
