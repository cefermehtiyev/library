package az.ingress.model.request;

import az.ingress.model.enums.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private Long categoryId;
    private String title;
    private String author;
    private String bookCode;
    private String publisher;
    private String language;
    private String description;
    private Integer pages;
    @NotNull(message = "File must not be null")
    private MultipartFile file;
    private MultipartFile image;
    private Integer publicationYear;
}
