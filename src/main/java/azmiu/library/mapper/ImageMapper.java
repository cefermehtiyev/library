package azmiu.library.mapper;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.ImageEntity;

import java.math.BigDecimal;

public enum ImageMapper {
    IMAGE_MAPPER;

    public ImageEntity buildImageEntity(BookEntity bookEntity, String imagePath, String imageType, BigDecimal imageSize) {
        return ImageEntity.builder()
                .bookEntity(bookEntity)
                .imagePath(imagePath)
                .imageType(imageType)
                .imageSize(imageSize)
                .build();
    }
}
