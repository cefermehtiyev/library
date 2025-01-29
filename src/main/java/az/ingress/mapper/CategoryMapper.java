package az.ingress.mapper;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.CategoryEntity;
import az.ingress.model.enums.CategoryStatus;
import az.ingress.model.request.BookRequest;
import az.ingress.model.request.CategoryRequest;

public enum CategoryMapper {
    CATEGORY_MAPPER;

    public CategoryEntity buildCategoryEntity(CategoryRequest categoryRequest){
        return CategoryEntity
                .builder()
                .bookCategory(categoryRequest.getBookCategory())
                .categoryStatus(CategoryStatus.AVAILABLE)
                .build();
    }



}
