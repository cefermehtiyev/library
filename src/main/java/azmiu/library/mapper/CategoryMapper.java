package azmiu.library.mapper;

import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.entity.ImageEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.CategoryResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserResponse;
import org.springframework.data.domain.Page;

public enum CategoryMapper {
    CATEGORY_MAPPER;

    public CategoryEntity buildCategoryEntity(CategoryRequest categoryRequest, CommonStatusEntity commonStatus, ImageEntity imageEntity){
        return CategoryEntity
                .builder()
                .image(imageEntity)
                .bookCategory(categoryRequest.getBookCategory())
                .commonStatus(commonStatus)
                .build();
    }

    public CategoryResponse buildCategoryResponse(CategoryEntity categoryEntity){
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .imagePath(categoryEntity.getImage().getImagePath())
                .bookCategory(categoryEntity.getBookCategory())
                .status(categoryEntity.getCommonStatus().getStatus())
                .build();
    }

    public void updateCategory(CategoryEntity categoryEntity, CategoryRequest categoryRequest){
        categoryEntity.setBookCategory(categoryRequest.getBookCategory());
    }

    public PageableResponse<CategoryResponse> pageableUserResponse(Page<CategoryEntity> categoryEntityPage){
        return PageableResponse.<CategoryResponse>builder()
                .list(categoryEntityPage.map(this::buildCategoryResponse).toList())
                .currentPageNumber(categoryEntityPage.getNumber())
                .totalPages(categoryEntityPage.getTotalPages())
                .totalElements(categoryEntityPage.getTotalElements())
                .numberOfElements(categoryEntityPage.getNumberOfElements())
                .hasNextPage(categoryEntityPage.hasNext())
                .build();
    }


}
