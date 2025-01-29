package az.ingress.util;

import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static az.ingress.model.constants.FileConstants.UPLOAD_DIR;

public class FileStorageUtil {



    public static String saveFile(String fileName, byte[] fileData) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = UPLOAD_DIR + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileData);
        }

        return filePath;
    }



}
