package azmiu.library.model.response;

import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.enums.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private String title;
    private String author;
    private String publisher;
    private String bookCode;
    private String language;
    private String description;
    private CommonStatus status;
    private Long readCount;
    private Integer pages;
    private String filePath;
    private InventoryStatus inventoryStatus;
    private Integer publicationYear;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookResponse that = (BookResponse) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }
}
