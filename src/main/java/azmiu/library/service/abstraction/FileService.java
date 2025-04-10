package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BookInventoryEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void uploadFile(BookInventoryEntity book, MultipartFile file);

    void uploadImage(BookInventoryEntity book, MultipartFile file);

    ResponseEntity<InputStreamResource> downloadFile(Long id);

    ResponseEntity<InputStreamResource> downloadImage(Long id);

    void updateFile(BookInventoryEntity bookInventoryEntity, MultipartFile file);

    void updateImage(BookInventoryEntity bookInventoryEntity, MultipartFile image);


    void deleteFile(Long fileId);

    void deleteImage(Long imageId);


}
