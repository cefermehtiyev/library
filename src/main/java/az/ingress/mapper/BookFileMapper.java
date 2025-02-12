package az.ingress.mapper;

import az.ingress.dao.entity.BookEntity;
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

    public FileEntity buildFileEntity(BookEntity bookEntity,String filePath, BigDecimal size){
        return FileEntity.builder()
                .bookEntity(bookEntity)
                .filePath(filePath)
                .fileSize(size)
                .build();
    }

}
