package azmiu.library.controller;

import azmiu.library.service.abstraction.ImageService;
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
@RequestMapping("/v1/images")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/download-image")
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> downloadImage(@RequestParam Long id) {
        return imageService.downloadImage(id);
    }

    @DeleteMapping("/image/{imageId}")
    public void deleteImage(@PathVariable Long imageId){
        imageService.deleteImage(imageId);
    }

}
