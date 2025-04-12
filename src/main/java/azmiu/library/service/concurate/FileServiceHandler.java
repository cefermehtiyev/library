package azmiu.library.service.concurate;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.FileEntity;
import azmiu.library.dao.entity.ImageEntity;
import azmiu.library.dao.repository.FileRepository;
import azmiu.library.dao.repository.ImageRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.FileStorageFailureException;
import azmiu.library.exception.NotFoundException;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.FileService;
import azmiu.library.util.FileStorageUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static azmiu.library.exception.ErrorMessage.FILE_NOT_FOUND;
import static azmiu.library.mapper.BookFileMapper.FILE_MAPPER;
import static azmiu.library.mapper.ImageMapper.IMAGE_MAPPER;


@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceHandler implements FileService {
    private final BookInventoryService bookInventoryService;
    private final FileRepository fileRepository;
    private final ImageRepository imageRepository;

    @Override
    public void uploadFile(BookInventoryEntity book, MultipartFile file) {
        if (file.isEmpty()) {
            var fileEntity = FILE_MAPPER.buildFileEntity(book, null, null);
            fileRepository.save(fileEntity);
        } else {
            fileRepository.save(saveUploadedFile(book, file));
        }
    }

    private FileEntity saveUploadedFile(BookInventoryEntity bookInventoryEntity, MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            byte[] fileData = file.getBytes();
            var fileSize = BigDecimal.valueOf(file.getSize()).divide(BigDecimal.valueOf(1_048_576), 2, RoundingMode.HALF_UP);
            var filePath = FileStorageUtil.saveFile(fileName, fileData, false);
            return FILE_MAPPER.buildFileEntity(bookInventoryEntity, filePath, fileSize);
        } catch (IOException ex) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }
    }

    @Override
    public void uploadImage(BookInventoryEntity book, MultipartFile image) {
        if (image.isEmpty()) {
            var imageEntity = IMAGE_MAPPER.buildImageEntity(book, null, null, null);
            imageRepository.save(imageEntity);
        } else {

            imageRepository.save(saveUploadedImage(book, image));
        }


    }

    private ImageEntity saveUploadedImage(BookInventoryEntity book, MultipartFile image) {
        try {
            String fileName = image.getOriginalFilename();
            byte[] fileData = image.getBytes();
            var fileSize = BigDecimal.valueOf(image.getSize()).divide(BigDecimal.valueOf(1_048_576), 2, RoundingMode.HALF_UP);
            var fileType = image.getContentType();

            var filePath = FileStorageUtil.saveFile(fileName, fileData, true);
            return IMAGE_MAPPER.buildImageEntity(book, filePath, fileType, fileSize);
        } catch (IOException ex) {
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

    @Override
    public void updateFile(BookInventoryEntity bookInventoryEntity, MultipartFile file) {

        var fileEntity = saveUploadedFile(bookInventoryEntity, file);
        System.out.println(bookInventoryEntity.getFile());
        var updatedFile = bookInventoryEntity.getFile();
        updatedFile.setFileSize(fileEntity.getFileSize());
        updatedFile.setFilePath(fileEntity.getFilePath());
        fileRepository.save(updatedFile);
        log.info("Files updated");


    }

    @Override
    public void updateImage(BookInventoryEntity bookInventoryEntity, MultipartFile image) {
        var imageEntity = saveUploadedImage(bookInventoryEntity, image);
        var updatedImage = bookInventoryEntity.getImage();
        updatedImage.setImagePath(imageEntity.getImagePath());
        updatedImage.setImageSize(imageEntity.getImageSize());
        updatedImage.setImageType(imageEntity.getImageType());
        imageRepository.save(updatedImage);
        log.info("Image Updated");
    }

    @Override
    public void deleteFile(Long id) {
        var fileEntity = findFileById(id);
        fileEntity.setFilePath(null);
        fileEntity.setFileSize(null);
        fileRepository.save(fileEntity);
    }


    @Override
    public void deleteImage(Long imageId) {
        var imageEntity = findImageById(imageId);
        imageEntity.setImagePath(null);
        imageEntity.setImageSize(null);
        imageEntity.setImageType(null);
        imageRepository.save(imageEntity);
    }


    private FileEntity findFileById(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(
                () -> new NotFoundException(FILE_NOT_FOUND.getMessage())
        );
    }

    private ImageEntity findImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(
                () -> new NotFoundException(FILE_NOT_FOUND.getMessage())
        );
    }


    private ResponseEntity<InputStreamResource> getFileResource(String filePath, boolean isImage) {
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
                throw new NotFoundException(FILE_NOT_FOUND.getMessage());
            }
        }
    }


}
