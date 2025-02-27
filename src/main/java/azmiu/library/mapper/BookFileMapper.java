package azmiu.library.mapper;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.FileEntity;
import azmiu.library.model.response.BookFileResponse;

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
