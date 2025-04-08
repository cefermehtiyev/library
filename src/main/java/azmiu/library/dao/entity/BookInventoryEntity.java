package azmiu.library.dao.entity;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "book_inventory")
@FieldDefaults(level = PRIVATE)
public class BookInventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    Integer publicationYear;
    Integer reservedQuantity;
    Integer borrowedQuantity;
    Integer availableQuantity;
    Long readCount;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(
            mappedBy = "bookInventory",
            cascade = {PERSIST, MERGE,REMOVE}
    )
    @ToString.Exclude
    RatingDetailsEntity ratingDetails;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST,REMOVE},
            mappedBy = "bookInventory"
    )
    List<RatingEntity> ratings;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST,REMOVE},
            mappedBy = "bookInventory"
    )
    List<BookEntity> books;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinColumn(name = "inventory_status")
    @ToString.Exclude
    InventoryStatusEntity inventoryStatus;


    @OneToOne(
            mappedBy = "bookInventory",
            cascade = {MERGE, PERSIST,REMOVE}
    )
    @ToString.Exclude
    ImageEntity image;

    @OneToOne(
            mappedBy = "bookInventory",
            cascade = {MERGE, PERSIST,REMOVE},
            fetch = LAZY
    )
    @ToString.Exclude
    FileEntity file;

    @ManyToOne
            (
                    fetch = LAZY,
                    cascade = {MERGE, PERSIST}
            )
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    CategoryEntity category;

    @OneToMany(
            cascade = {MERGE,PERSIST,REMOVE},
            fetch = LAZY,
            mappedBy = "bookInventory"
    )
    Set<SavedBookEntity> savedBooks;

    @ManyToOne(
            cascade = {MERGE,PERSIST},
            fetch = LAZY
    )
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    CommonStatusEntity commonStatus;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookInventoryEntity that = (BookInventoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
