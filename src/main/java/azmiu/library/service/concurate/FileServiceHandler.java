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
import azmiu.library.util.DownloadUtil;
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
    private final FileRepository fileRepository;

    @Override
    public FileEntity uploadFile( MultipartFile file) {
        if (file.isEmpty()) {
            var fileEntity = FILE_MAPPER.buildFileEntity( null, null);
            fileRepository.save(fileEntity);
            return fileEntity;
        } else {
            var fileEntity = saveUploadedFile(file);
            fileRepository.save(fileEntity);
            return fileEntity;
        }
    }

    private FileEntity saveUploadedFile( MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            byte[] fileData = file.getBytes();
            var fileSize = BigDecimal.valueOf(file.getSize()).divide(BigDecimal.valueOf(1_048_576), 2, RoundingMode.HALF_UP);
            var filePath = FileStorageUtil.saveFile(fileName, fileData, false);
            return FILE_MAPPER.buildFileEntity(filePath, fileSize);
        } catch (IOException ex) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }
    }


    @Override
    public ResponseEntity<InputStreamResource> downloadFile(Long id) {
        var filePath = findFileById(id).getFilePath();
        return DownloadUtil.getFileResource(filePath, false);
    }


    @Override
    public void updateFile(BookInventoryEntity bookInventoryEntity, MultipartFile file) {

        var fileEntity = saveUploadedFile( file);
        var updatedFile = bookInventoryEntity.getFile();
        updatedFile.setFileSize(fileEntity.getFileSize());
        updatedFile.setFilePath(fileEntity.getFilePath());
        fileRepository.save(updatedFile);
        log.info("Files updated");


    }


    @Override
    public void deleteFile(Long id) {
        log.info("Removed file id: {}",id);
        var fileEntity = findFileById(id);
        fileEntity.setFilePath(null);
        fileEntity.setFileSize(null);
        fileRepository.save(fileEntity);
    }


    private FileEntity findFileById(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(
                () -> new NotFoundException(FILE_NOT_FOUND.getMessage())
        );
    }







}
