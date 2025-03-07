package azmiu.library.controller;

import azmiu.library.service.abstraction.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/files")
public class FileController {
    private final FileService fileService;



    @PostMapping(value = "/upload-files/{bookId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('SUPER_ADMIN') || hasRole('ADMIN')")
    public void uploadFiles(@PathVariable Long bookId,
                            @RequestParam(value = "file", required = false) MultipartFile file,
                            @RequestParam(value = "image", required = false) MultipartFile image) {
        fileService.uploadFile(bookId,file);
        fileService.uploadFile(bookId,image);
    }


    @GetMapping("/dowload-file")
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam Long id) {
        return fileService.downloadFile(id);
    }

    @GetMapping("/dowload-image")
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> downloadImage(@RequestParam Long id) {
        return fileService.downloadFile(id);
    }
}
