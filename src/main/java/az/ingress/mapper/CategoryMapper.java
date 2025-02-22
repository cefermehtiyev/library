package az.ingress.mapper;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.model.enums.BookCategory;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.CategoryResponse;

public enum CategoryMapper {
    CATEGORY_MAPPER;

    public CategoryEntity buildCategoryEntity(CategoryRequest categoryRequest, CommonStatusEntity commonStatus){
        return CategoryEntity.builder()
                .bookCategory(categoryRequest.getBookCategory())
                .commonStatusEntity(commonStatus)
                .build();
    }

    public CategoryResponse buildCategoryResponse(CategoryEntity categoryEntity){
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .bookCategory(categoryEntity.getBookCategory())
                .status(categoryEntity.getCommonStatusEntity().getStatus())
                .build();
    }

    public CategoryRequest buildCategoryRequest(BookCategory bookCategory){
        return CategoryRequest.builder()
                .bookCategory(bookCategory)
                .build();

    }


}
