package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.ImageEntity;

import java.math.BigDecimal;

public enum ImageMapper {
    IMAGE_MAPPER;

    public ImageEntity buildImageEntity(BookInventoryEntity bookEntity, String imagePath, String imageType, BigDecimal imageSize) {
        return ImageEntity.builder()
                .bookInventory(bookEntity)
                .imagePath(imagePath)
                .imageType(imageType)
                .imageSize(imageSize)
                .build();
    }
}
