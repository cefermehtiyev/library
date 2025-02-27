package azmiu.library.mapper;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookBorrowHistoryEntity;
import azmiu.library.dao.entity.BorrowStatusEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.response.BookBorrowHistoryResponse;

import java.time.LocalDate;

import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;

public enum BookBorrowHistoryMapper {
    BOOK_LOAN_HISTORY_MAPPER;

    public<T extends UserEntity> BookBorrowHistoryEntity buildBookBorrowHistoryEntity(T user, BookEntity book, BorrowStatusEntity status){
        return BookBorrowHistoryEntity.builder()
                .book(book)
                .user(user)
                .borrowStatusEntity(status)
                .build();
    }

    public BookBorrowHistoryResponse buildBookBorrowHistoryResponse(BookBorrowHistoryEntity bookBorrowHistory, BookEntity bookEntity){
        return BookBorrowHistoryResponse.builder()
                .bookResponse(BOOK_MAPPER.buildBookResponse(bookEntity))
                .borrowStatus(bookBorrowHistory.getBorrowStatusEntity().getStatus())
                .borrowDate(bookBorrowHistory.getBorrowDate())
                .returnedDate(bookBorrowHistory.getReturnDate())
                .build();

    }

    public void updateReturnBookBorrowHistory(BookBorrowHistoryEntity bookBorrowHistory, BorrowStatusEntity status){
        bookBorrowHistory.setBorrowStatusEntity(status);
        bookBorrowHistory.setReturnDate(LocalDate.now());
    }
}
