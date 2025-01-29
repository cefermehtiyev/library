package az.ingress.service.abstraction;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);
    ResponseEntity<InputStreamResource> downloadFile(@RequestParam String filePath);

}
