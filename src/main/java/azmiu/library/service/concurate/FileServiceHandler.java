package azmiu.library.service.concurate;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.repository.FileRepository;
import azmiu.library.dao.repository.ImageRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.FileStorageFailureException;
import azmiu.library.exception.NotFoundException;
import azmiu.library.service.abstraction.BookService;
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
import java.util.Objects;

import static azmiu.library.mapper.BookFileMapper.FILE_MAPPER;
import static azmiu.library.mapper.ImageMapper.IMAGE_MAPPER;


@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceHandler implements FileService {
    private final BookService bookService;
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

                FileStorageUtil.saveFile(fileName, fileData, true);
                var imageEntity = IMAGE_MAPPER.buildImageEntity(book, fileName, fileType, fileSize);
                imageRepository.save(imageEntity);
            }


        } catch (IOException e) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }


    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(Long id) {
//        var filePath = bookService.fetchEntityExist(id).getFileEntity().getFilePath();
        return null;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadImage(Long id) {
//        var imagePath = bookService.fetchEntityExist(id).().getFilePath();
        return null;
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
