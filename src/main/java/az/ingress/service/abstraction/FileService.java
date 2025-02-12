package az.ingress.service.abstraction;

import az.ingress.dao.entity.BookEntity;
import az.ingress.model.response.BookFileResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void uploadFile(MultipartFile file, BookEntity bookEntity);
    ResponseEntity<InputStreamResource> downloadFile(@RequestParam Long id);
    ResponseEntity<InputStreamResource> downloadImage(@RequestParam Long id);



}
