package az.ingress.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "images")
@Entity
public class ImageEntity {

    @Id
    private Long id;
    private String imagePath;
    private String imageType;
    private BigDecimal imageSize;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @ToString.Exclude
    BookEntity bookEntity;

}
