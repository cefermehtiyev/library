package azmiu.library.service.concurate;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.repository.FileRepository;
import azmiu.library.dao.repository.ImageRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.FileStorageFailureException;
import azmiu.library.exception.NotFoundException;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.FileService;
import azmiu.library.util.FileStorageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static azmiu.library.mapper.BookFileMapper.FILE_MAPPER;
import static azmiu.library.mapper.ImageMapper.IMAGE_MAPPER;


@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceHandler implements FileService {
    private final BookInventoryService bookInventoryService;
    private final FileRepository fIleRepository;
    private final ImageRepository imageRepository;

    @Override
    public void uploadFile(BookInventoryEntity book, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                var fileEntity = FILE_MAPPER.buildFileEntity(book, null, null);
                fIleRepository.save(fileEntity);
            } else {
                String fileName = file.getOriginalFilename();
                byte[] fileData = file.getBytes();
                var fileSize = BigDecimal.valueOf(file.getSize()).divide(BigDecimal.valueOf(1_048_576), 2, RoundingMode.HALF_UP);


                var filePath = FileStorageUtil.saveFile(fileName, fileData, false);
                var fileEntity = FILE_MAPPER.buildFileEntity(book, filePath, fileSize);
                fIleRepository.save(fileEntity);
            }


        } catch (IOException e) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }


    }

    @Override
    public void uploadImage(BookInventoryEntity book, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                var imageEntity = IMAGE_MAPPER.buildImageEntity(book, null, null, null);
                imageRepository.save(imageEntity);

            } else {
                String fileName = file.getOriginalFilename();
                byte[] fileData = file.getBytes();
                var fileSize = BigDecimal.valueOf(file.getSize()).divide(BigDecimal.valueOf(1_048_576), 2, RoundingMode.HALF_UP);
                var fileType = file.getContentType();

                var filePath = FileStorageUtil.saveFile(fileName, fileData, true);
                var imageEntity = IMAGE_MAPPER.buildImageEntity(book, filePath, fileType, fileSize);
                imageRepository.save(imageEntity);
            }


        } catch (IOException e) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }


    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(Long id) {
        var filePath = bookInventoryService.getBookInventoryEntity(id).getFile().getFilePath();
        return getFileResource(filePath, false);
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadImage(Long id) {
        var imagePath = bookInventoryService.getBookInventoryEntity(id).getImage().getImagePath();
        return getFileResource(imagePath, true);
    }


    private ResponseEntity<InputStreamResource> getFileResource(String filePath, boolean isImage) {
        try {
            File file = new File(filePath);
            log.info("FilePath: {}", filePath);

            if (!file.exists()) {
                if (isImage) {
                    throw new NotFoundException(ErrorMessage.IMAGE_NOT_FOUND.getMessage());
                } else {
                    throw new NotFoundException(ErrorMessage.FILE_NOT_FOUND.getMessage());
                }
            }

            FileInputStream fileInputStream = new FileInputStream(file);
            HttpHeaders headers = new HttpHeaders();

            // Fayl adını UTF-8 ilə URL-encoded edirik və filename*= atributunu istifadə edirik
            String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);

            // Faylın MIME tipini müəyyən edirik
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream"; // default tip
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
                throw new NotFoundException(ErrorMessage.FILE_NOT_FOUND.getMessage());
            }
        }
    }


}
