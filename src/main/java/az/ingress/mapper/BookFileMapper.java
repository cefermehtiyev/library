package az.ingress.mapper;

import az.ingress.dao.entity.FileEntity;
import az.ingress.model.response.BookFileResponse;

import java.math.BigDecimal;

public enum BookFileMapper {
    FILE_MAPPER;

    public BookFileResponse buildFileResponse(String filePath, BigDecimal size){
        return BookFileResponse.builder()
                .filePath(filePath)
                .fileSize(size)
                .build();
    }

    public FileEntity buildFileEntity(String filePath, BigDecimal size){
        return FileEntity.builder()
                .filePath(filePath)
                .fileSize(size)
                .build();
    }

}
