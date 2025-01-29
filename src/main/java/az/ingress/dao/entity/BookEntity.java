package az.ingress.dao.entity;

import az.ingress.model.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BookEntity  {
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
    private String filePath;
    @Enumerated(STRING)
    private BookStatus bookStatus;
    private Integer publicationYear;
    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE,PERSIST,REMOVE}
    )
    @JoinColumn( name = "category_id" )
    @ToString.Exclude
    CategoryEntity category;

    @ManyToOne(fetch = LAZY,
            cascade = {MERGE, PERSIST})
    @JoinColumn(name = "inventory_id")
    @ToString.Exclude
    BookInventoryEntity bookInventoryEntity;

    @ManyToMany(
            fetch = LAZY,
            cascade = {MERGE,PERSIST}
    )
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    List<AuthorEntity> authorEntities;

    @ManyToMany(
            fetch = LAZY,
            cascade = {MERGE,PERSIST},
            mappedBy = "bookEntities"
    )
    @ToString.Exclude
    @JsonBackReference
    List<StudentEntity> studentEntities;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE,PERSIST},
            mappedBy = "book"
    )
    List<BookBorrowHistoryEntity> bookBorrowHistoryEntity;



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