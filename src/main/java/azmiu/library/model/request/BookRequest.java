package azmiu.library.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookRequest {
    private Long categoryId;
    private String title;
    private String author;
    private String bookCode;
    private String publisher;
    private String language;
    private String description;
    private Integer pages;
    private Integer publicationYear;
}
