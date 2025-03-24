package azmiu.library.dao.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book_borrowing")
@FieldDefaults(level = PRIVATE)
public class BookBorrowingEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @CreationTimestamp
    LocalDate borrowDate;
    LocalDate returnDate;

    @ManyToOne(fetch = LAZY,
            cascade = {MERGE, PERSIST})
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne(
            fetch = LAZY,
            cascade = {PERSIST, MERGE}
    )
    @JoinColumn(name = "book_id")
    BookEntity book;


    @ManyToOne(
            fetch = LAZY,
            cascade = {PERSIST, MERGE}
    )
    @JoinColumn(name = "borrow_status")
    BorrowStatusEntity borrowStatus;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookBorrowingEntity that = (BookBorrowingEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
