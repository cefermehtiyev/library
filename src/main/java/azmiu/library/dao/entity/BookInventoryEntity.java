package azmiu.library.dao.entity;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Table(name = "book_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class BookInventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer publicationYear;
    private Integer reservedQuantity;
    private Integer borrowedQuantity;
    private Integer availableQuantity;
    private Long readCount;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST, REMOVE},
            mappedBy = "bookInventory"
    )
    List<RatingEntity> ratings;

    @OneToMany(
            fetch = LAZY,
            cascade = {MERGE, PERSIST, REMOVE},
            mappedBy = "bookInventory"
    )
    List<BookEntity> books;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST, REMOVE}
    )
    @JoinColumn(name = "inventory_status")
    @ToString.Exclude
    InventoryStatusEntity inventoryStatus;


    @OneToOne(
            mappedBy = "bookInventory",
            cascade = {MERGE, PERSIST}
    )
    @ToString.Exclude
    ImageEntity image;

    @OneToOne(
            mappedBy = "bookInventory",
            cascade = {MERGE, PERSIST}
    )
    @ToString.Exclude
    FileEntity file;

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
