package azmiu.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "images")
@FieldDefaults(level = PRIVATE)
public class ImageEntity {

    @Id
    Long id;
    String imagePath;
    String imageType;
    BigDecimal imageSize;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @ToString.Exclude
    BookInventoryEntity bookInventory;

}
