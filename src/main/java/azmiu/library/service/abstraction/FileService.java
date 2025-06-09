package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.FileEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileEntity uploadFile(MultipartFile file);

    ResponseEntity<InputStreamResource> downloadFile(String path);

    void updateFile(BookInventoryEntity bookInventoryEntity, MultipartFile file);

    void deleteFile(Long fileId);
}
