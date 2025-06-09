package azmiu.library.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
@Builder
@FieldDefaults(level = PRIVATE)
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    Integer score;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinColumn(name = "book_inventory_id")
    BookInventoryEntity bookInventory;

    @ManyToOne(
            fetch = LAZY,
            cascade = {MERGE, PERSIST}
    )
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    CommonStatusEntity commonStatus;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RatingEntity that = (RatingEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
