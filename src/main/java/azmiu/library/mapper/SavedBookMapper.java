package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.SavedBookEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.SavedBookResponse;
import azmiu.library.model.response.StudentResponse;
import org.springframework.data.domain.Page;

import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;

public enum SavedBookMapper {
    SAVED_BOOK_MAPPER;

    public SavedBookEntity buildSavedBooKEntity(UserEntity user, BookInventoryEntity bookInventory){
        return SavedBookEntity.builder()
                .user(user)
                .bookInventory(bookInventory)
                .build();
    }

    public SavedBookResponse buildSavedBookResponse(SavedBookEntity savedBook){
        return SavedBookResponse.builder()
                .id(savedBook.getId())
                .createdAt(savedBook.getCreatedAt())
                .bookResponse(BOOK_MAPPER.buildBookResponse(savedBook.getBookInventory().getBooks().getFirst()))
                .build();
    }

    public PageableResponse<SavedBookResponse> buildPageableResponse(Page<SavedBookEntity> savedBookEntityPage){
        return PageableResponse.<SavedBookResponse>builder()
                .list(savedBookEntityPage.map(this::buildSavedBookResponse).toList())
                .currentPageNumber(savedBookEntityPage.getNumber())
                .totalPages(savedBookEntityPage.getTotalPages())
                .totalElements(savedBookEntityPage.getTotalElements())
                .numberOfElements(savedBookEntityPage.getNumberOfElements())
                .hasNextPage(savedBookEntityPage.hasNext())
                .build();
    }


}
