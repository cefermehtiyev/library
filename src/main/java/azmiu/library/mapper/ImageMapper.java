package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.ImageEntity;

import java.math.BigDecimal;

public enum ImageMapper {
    IMAGE_MAPPER;

    public ImageEntity buildImageEntity(String imagePath, String imageType, BigDecimal imageSize) {
        return ImageEntity.builder()
                .imagePath(imagePath)
                .imageType(imageType)
                .imageSize(imageSize)
                .build();
    }
}
