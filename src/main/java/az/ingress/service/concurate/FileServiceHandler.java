package az.ingress.service.concurate;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.repository.FileRepository;
import az.ingress.dao.repository.ImageRepository;
import az.ingress.dao.repository.StudentRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.FileStorageFailureException;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.ImageMapper;
import az.ingress.service.abstraction.BookService;
import az.ingress.service.abstraction.FileService;
import az.ingress.util.FileStorageUtil;

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
import java.util.Objects;

import static az.ingress.mapper.BookFileMapper.FILE_MAPPER;
import static az.ingress.mapper.ImageMapper.IMAGE_MAPPER;


@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceHandler implements FileService {
    private final BookService bookService;
    private final FileRepository fIleRepository;
    private final ImageRepository imageRepository;

    @Override
    public void uploadFile(MultipartFile file, BookEntity bookEntity) {

        try {
            String fileName = file.getOriginalFilename();
            byte[] fileData = file.getBytes();
            var fileSize = BigDecimal.valueOf(file.getSize()).divide(BigDecimal.valueOf(1_048_576), 2, RoundingMode.HALF_UP);
            var fileType = file.getContentType();

            if (Objects.requireNonNull(fileType).startsWith("image")) {
                FileStorageUtil.saveFile(fileName, fileData, true);
                var imageEntity = IMAGE_MAPPER.buildImageEntity(bookEntity, fileName, fileType, fileSize);
                imageRepository.save(imageEntity);
            } else {
                var filePath = FileStorageUtil.saveFile(fileName, fileData, false);
                var fileEntity = FILE_MAPPER.buildFileEntity(bookEntity, filePath, fileSize);
                fIleRepository.save(fileEntity);
            }


        } catch (IOException e) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(Long id) {
        var filePath = bookService.fetchEntityExist(id).getFileEntity().getFilePath();
        return getFile(filePath);
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadImage(Long id) {
        var imagePath = bookService.fetchEntityExist(id).getFileEntity().getFilePath();
        return getFile(imagePath);
    }

    private ResponseEntity<InputStreamResource> getFile(String filePath) {

        try {
            File file = new File(filePath);

            log.info("FilePath: {}", filePath);

            if (!file.exists()) {
                throw new NotFoundException(ErrorMessage.FILE_NOT_FOUND.getMessage());
            }

            FileInputStream fileInputStream = new FileInputStream(file);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(fileInputStream));
        } catch (IOException e) {
            throw new NotFoundException(ErrorMessage.FILE_NOT_FOUND.getMessage());
        }
    }

}
