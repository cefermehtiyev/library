package az.ingress.util;

import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.constants.FileConstants;
import liquibase.pro.packaged.S;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static az.ingress.model.constants.FileConstants.IMAGE_DIR;
import static az.ingress.model.constants.FileConstants.UPLOAD_DIR;

public class FileStorageUtil {


    public static String saveFile(String fileName, byte[] fileData, boolean isImage) throws IOException {
        var fileAdress = isImage ? IMAGE_DIR : UPLOAD_DIR;

        String filePath = fileAdress + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileData);
        }

        return filePath;
    }


}
