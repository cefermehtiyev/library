package az.ingress.service.abstraction;

import az.ingress.model.response.FileResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileResponse uploadFile(MultipartFile file);
    ResponseEntity<InputStreamResource> downloadFile(@RequestParam Long id);

}
