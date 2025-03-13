package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void uploadFile(BookInventoryEntity book, MultipartFile file);
    void uploadImage(BookInventoryEntity book, MultipartFile file);
    ResponseEntity<InputStreamResource> downloadFile(@RequestParam Long id);
    ResponseEntity<InputStreamResource> downloadImage(@RequestParam Long id);



}
