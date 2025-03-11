package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
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

    public FileEntity buildFileEntity(BookInventoryEntity bookInventoryEntity, String filePath, BigDecimal size){
        return FileEntity.builder()
                .bookInventory(bookInventoryEntity)
                .filePath(filePath)
                .fileSize(size)
                .build();
    }

}
