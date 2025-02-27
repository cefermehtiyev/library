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

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book_files")
@Entity
public class FileEntity {

    @Id
    private Long id;

    private String filePath;
    private BigDecimal fileSize;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @ToString.Exclude
    BookEntity bookEntity;

}
