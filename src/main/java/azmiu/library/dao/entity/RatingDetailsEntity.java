package azmiu.library.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating_details")
@Entity
public class RatingDetailsEntity {
    @Id
    private Long bookInventoryId;
    private Integer voteCount;
    private BigDecimal averageRating;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RatingDetailsEntity that = (RatingDetailsEntity) o;
        return Objects.equals(bookInventoryId, that.bookInventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookInventoryId);
    }
}
