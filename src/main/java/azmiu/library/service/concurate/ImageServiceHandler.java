package azmiu.library.service.concurate;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.ImageEntity;
import azmiu.library.dao.repository.ImageRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.FileStorageFailureException;
import azmiu.library.exception.NotFoundException;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.ImageService;
import azmiu.library.util.DownloadUtil;
import azmiu.library.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
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
import static azmiu.library.exception.ErrorMessage.IMAGE_NOT_FOUND;
import static azmiu.library.mapper.ImageMapper.IMAGE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceHandler implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public ImageEntity uploadImage( MultipartFile image) {
        if (image.isEmpty()) {
            var imageEmptyEntity = IMAGE_MAPPER.buildImageEntity(null, null, null);
            imageRepository.save(imageEmptyEntity);
            return imageEmptyEntity;
        } else {
            var imageEntity = saveUploadedImage(image);
            imageRepository.save(imageEntity);
            return imageEntity;
        }
    }

    private ImageEntity saveUploadedImage(MultipartFile image) {
        try {
            String fileName = image.getOriginalFilename();
            byte[] fileData = image.getBytes();
            var fileSize = BigDecimal.valueOf(image.getSize()).divide(BigDecimal.valueOf(1_048_576), 2, RoundingMode.HALF_UP);
            var fileType = image.getContentType();

            var filePath = FileStorageUtil.saveFile(fileName, fileData, true);
            return IMAGE_MAPPER.buildImageEntity(filePath, fileType, fileSize);
        } catch (IOException ex) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadImage(Long id) {
        var imagePath = findImageById(id).getImagePath();
        return DownloadUtil.getFileResource(imagePath,true);
    }



    @Override
    public void updateImage(BookInventoryEntity bookInventoryEntity, MultipartFile image) {
        var imageEntity = saveUploadedImage(image);
        var updatedImage = bookInventoryEntity.getImage();
        updatedImage.setImagePath(imageEntity.getImagePath());
        updatedImage.setImageSize(imageEntity.getImageSize());
        updatedImage.setImageType(imageEntity.getImageType());
        imageRepository.save(updatedImage);
        log.info("Image Updated");
    }

    @Override
    public void deleteImage(Long imageId) {
        var imageEntity = findImageById(imageId);
        IMAGE_MAPPER.removeImageDetails(imageEntity);
        imageRepository.save(imageEntity);
    }

    private ImageEntity findImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(
                () -> new NotFoundException(IMAGE_NOT_FOUND.getMessage())
        );
    }






}
