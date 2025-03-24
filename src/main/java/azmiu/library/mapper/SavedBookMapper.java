package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.SavedBookEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.response.SavedBookResponse;

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
                .bookResponse(BookMapper.BOOK_MAPPER.buildBookResponse(savedBook.getBookInventory().getBooks().getFirst()))
                .build();
    }
}
