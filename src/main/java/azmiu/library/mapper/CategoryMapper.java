package azmiu.library.mapper;

import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.model.enums.BookCategory;
import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.CategoryResponse;

public enum CategoryMapper {
    CATEGORY_MAPPER;

    public CategoryEntity buildCategoryEntity(CategoryRequest categoryRequest, CommonStatusEntity commonStatus){
        return CategoryEntity.builder()
                .bookCategory(categoryRequest.getBookCategory())
                .commonStatus(commonStatus)
                .build();
    }

    public CategoryResponse buildCategoryResponse(CategoryEntity categoryEntity){
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .bookCategory(categoryEntity.getBookCategory())
                .status(categoryEntity.getCommonStatus().getStatus())
                .build();
    }

    public CategoryRequest buildCategoryRequest(BookCategory bookCategory){
        return CategoryRequest.builder()
                .bookCategory(bookCategory)
                .build();

    }


}
