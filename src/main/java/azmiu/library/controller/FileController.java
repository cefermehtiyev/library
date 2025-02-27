package azmiu.library.controller;

import azmiu.library.service.abstraction.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/upload-file")
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> uploadBook(@RequestParam Long id) {
        return fileService.downloadFile(id);
    }

    @GetMapping("/upload-image")
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> uploadImage(@RequestParam Long id) {
        return fileService.downloadFile(id);
    }
}
