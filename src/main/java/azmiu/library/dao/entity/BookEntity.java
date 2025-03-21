package azmiu.library.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String bookCode;
    private String author;
    private String publisher;
    private String language;
    private String description;
    private Integer pages;
    private Integer publicationYear;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST, REMOVE}
    )
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    CommonStatusEntity commonStatus;


    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST, REMOVE}
    )
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    CategoryEntity category;

    @ManyToOne(fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinColumn(name = "inventory_id")
    @ToString.Exclude
    BookInventoryEntity bookInventory;

    @ManyToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    List<AuthorEntity> authors;


    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST},
            mappedBy = "book"
    )
    List<BookBorrowingEntity> bookBorrowing;

    @ManyToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST},
            mappedBy = "books"
    )
    @ToString.Exclude
    @JsonBackReference
    List<UserEntity> users;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BookEntity book = (BookEntity) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }


}