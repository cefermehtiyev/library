package azmiu.library.controller;

import azmiu.library.service.abstraction.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/download-file")
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String path) {
        return fileService.downloadFile(path);
    }


    @DeleteMapping("{id}")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }
}
