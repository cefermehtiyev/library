package azmiu.library.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating_cache_outbox")
@Entity
@Builder
public class RatingCacheOutboxEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long bookInventoryId;
    private Integer voteCount;
    private BigDecimal averageRating;
    private Boolean processed;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RatingCacheOutboxEntity that = (RatingCacheOutboxEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}