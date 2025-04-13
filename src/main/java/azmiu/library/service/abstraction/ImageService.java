package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.FileEntity;
import azmiu.library.dao.entity.ImageEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ImageEntity uploadImage(MultipartFile file);

    ResponseEntity<InputStreamResource> downloadImage(Long id);


    void updateImage(BookInventoryEntity bookInventoryEntity, MultipartFile image);


    void deleteImage(Long imageId);


}
