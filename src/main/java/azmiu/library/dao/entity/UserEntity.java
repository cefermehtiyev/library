package azmiu.library.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
@FieldDefaults(level = PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String userName;
    String email;
    String password;
    String firstName;
    String lastName;
    String fin;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;


    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST},
            mappedBy = "user"
    )
    List<RatingEntity> ratings;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    CommonStatusEntity commonStatus;

    @OneToOne(
            mappedBy = "user",
            cascade = {PERSIST, MERGE}
    )
    @ToString.Exclude
    EmployeeEntity employee;

    @OneToOne(
            mappedBy = "user",
            cascade = {PERSIST, MERGE}
    )
    @ToString.Exclude
    AdminEntity admin;

    @OneToOne(
            mappedBy = "user",
            cascade = {PERSIST, MERGE}
    )
    @ToString.Exclude
    StudentEntity student;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST},
            mappedBy = "user"
    )
    @ToString.Exclude
    List<BookBorrowingEntity> bookBorrowing;

    @ManyToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinTable(
            name = "book_borrowing",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @ToString.Exclude
    List<BookEntity> books;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinColumn(name = "role_id")
    @ToString.Exclude
    UserRoleEntity userRole;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST},
            mappedBy = "user"
    )
    Set<SavedBookEntity> savedBooks;



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
