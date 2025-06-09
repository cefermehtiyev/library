package azmiu.library.util;




import java.io.FileOutputStream;
import java.io.IOException;

import static azmiu.library.model.constants.FileConstants.IMAGE_DIR;
import static azmiu.library.model.constants.FileConstants.UPLOAD_DIR;

public class FileStorageUtil {


    public static String saveFile(String fileName, byte[] fileData, boolean isImage) throws IOException {
        var fileAddress = isImage ? IMAGE_DIR : UPLOAD_DIR;

        String filePath = fileAddress + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileData);
        }

        return filePath;
    }


}
