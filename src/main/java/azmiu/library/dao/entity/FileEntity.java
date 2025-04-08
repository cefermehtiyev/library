package azmiu.library.dao.entity;

import jakarta.persistence.Cache;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book_files")
@FieldDefaults(level = PRIVATE)
public class FileEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String filePath;
    BigDecimal fileSize;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "inventory_id",nullable = false)
    @ToString.Exclude
    BookInventoryEntity bookInventory;

}
