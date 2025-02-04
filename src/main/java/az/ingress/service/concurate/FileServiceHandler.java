package az.ingress.service.concurate;

import az.ingress.controller.BookController;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.repository.BookRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.FileStorageFailureException;
import az.ingress.exception.InvalidFileURLException;
import az.ingress.exception.NotFoundException;
import az.ingress.service.abstraction.BookService;
import az.ingress.service.abstraction.FileService;
import az.ingress.service.abstraction.UserService;
import az.ingress.util.FileStorageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceHandler implements FileService {
    private final BookRepository bookRepository;

    @Override
    public String uploadFile(MultipartFile file) {

        try {
            String fileName = file.getOriginalFilename();
            byte[] fileData = file.getBytes();

            String filePath = FileStorageUtil.saveFile(fileName, fileData);
            log.info("File Name: {}", file);

            String normalizedPath = filePath.replace("\\", "/");
            log.info("Normalized File Path: {}", normalizedPath);

            return normalizedPath;

        } catch (IOException e) {
            throw new FileStorageFailureException(ErrorMessage.FILE_STORAGE_FAILURE.getMessage());
        }
    }


    @Override
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String bookCode) {


        var filePath = fetchEntityExist(bookCode).getFilePath();

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

    private BookEntity fetchEntityExist(String bookCode) {
        return bookRepository.findByBookCode(bookCode).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND.getMessage())
        );
    }

//    private final String bucketName = "azmui-library-map"; // Bucket adı
//    private final String credentialsPath = "C:\\Users\\cefer\\desktop1\\azmui\\library\\src\\main\\resources\\credentials.json"; // GCP JSON açar faylının yolu
//    private final Storage storage;
//
//    public FileServiceHandler() throws IOException {
//        // Google Cloud Storage müştərisini qurmaq
//        storage = StorageOptions.newBuilder()
//                .setCredentials(
//                        GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
//                )
//                .build()
//                .getService();
//    }
//
//    public String uploadFile(MultipartFile file) {
//        try {
//            String fileName = file.getOriginalFilename();
//            byte[] fileData = file.getBytes();
//
//            // Faylı Google Cloud Storage-a yükləmək
//            BlobId blobId = BlobId.of(bucketName, fileName);
//            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//
//            storage.create(blobInfo, fileData);
//            String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
//            System.out.println("Fayl uğurla yükləndi: " + fileUrl);
//
//            return fileUrl;
//        } catch (IOException e) {
//            throw new RuntimeException("Fayl yüklənərkən xəta baş verdi: " + e.getMessage(), e);
//        }
//    }
//
//    public ResponseEntity<InputStreamResource> downloadFile(String filePath) {
//        try {
//            String fileName = new File(filePath).getName();
//
//            // Google Cloud Storage-dən faylı əldə etmək
//            Blob blob = storage.get(bucketName, fileName);
//
//            if (blob == null || !blob.exists()) {
//                throw new RuntimeException("Fayl tapılmadı: " + fileName);
//            }
//
//            InputStream inputStream = new ByteArrayInputStream(blob.getContent());
//
//            // HTTP başlıqlarını tənzimləyirik
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentLength(blob.getSize())
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(new InputStreamResource(inputStream));
//        } catch (Exception e) {
//            throw new RuntimeException("Fayl endirilərkən xəta baş verdi: " + e.getMessage(), e);
//        }
//    }


}
