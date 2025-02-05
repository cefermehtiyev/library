package az.ingress.mapper;

import az.ingress.model.response.FileResponse;

import java.math.BigDecimal;

public enum FileMapper {
    FILE_MAPPER;

    public FileResponse buildFileResponse(String filePath, BigDecimal size){
        return FileResponse.builder()
                .filePath(filePath)
                .size(size)
                .build();
    }

}
