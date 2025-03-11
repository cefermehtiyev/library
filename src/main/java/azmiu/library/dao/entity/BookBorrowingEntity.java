package azmiu.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book_borrowing")
@Entity
public class BookBorrowingEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY,
            cascade = {MERGE, PERSIST})
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(
            fetch = LAZY,
            cascade = {PERSIST,MERGE}
    )
    @JoinColumn(name = "book_id")
    private BookEntity book;


    @CreationTimestamp
    private LocalDate borrowDate;
    private LocalDate returnDate;

    @ManyToOne(
            fetch = LAZY,
            cascade = {PERSIST,MERGE}
    )
    @JoinColumn(name = "status_id")
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
