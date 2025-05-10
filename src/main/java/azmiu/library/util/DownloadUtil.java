package azmiu.library.util;

import azmiu.library.annotation.Log;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static azmiu.library.exception.ErrorMessage.FILE_NOT_FOUND;
@Slf4j
public class DownloadUtil {

    public static ResponseEntity<InputStreamResource> getFileResource(String filePath, boolean isImage) {
        try {
            File file = new File(filePath);
            log.info("FilePath: {}", filePath);

            if (!file.exists()) {
                if (isImage) {
                    throw new NotFoundException(ErrorMessage.IMAGE_NOT_FOUND.getMessage());
                } else {
                    throw new NotFoundException(FILE_NOT_FOUND.getMessage());
                }
            }

            FileInputStream fileInputStream = new FileInputStream(file);
            HttpHeaders headers = new HttpHeaders();

            String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);

            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new InputStreamResource(fileInputStream));
        } catch (IOException | NullPointerException e) {
            if (isImage) {
                throw new NotFoundException(ErrorMessage.IMAGE_NOT_FOUND.getMessage());
            } else {
                throw new NotFoundException(FILE_NOT_FOUND.getMessage());
            }
        }
    }
}
