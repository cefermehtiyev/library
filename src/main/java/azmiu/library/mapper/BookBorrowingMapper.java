package azmiu.library.mapper;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookBorrowingEntity;
import azmiu.library.dao.entity.BorrowStatusEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.response.BookBorrowHistoryResponse;

import java.time.LocalDate;

import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;

public enum BookBorrowingMapper {
    BOOK_LOAN_HISTORY_MAPPER;

    public<T extends UserEntity> BookBorrowingEntity buildBookBorrowHistoryEntity(T user, BookEntity book, BorrowStatusEntity status){
        return BookBorrowingEntity.builder()
                .book(book)
                .user(user)
                .borrowStatus(status)
                .build();
    }

    public BookBorrowHistoryResponse buildBookBorrowHistoryResponse(BookBorrowingEntity bookBorrowHistory, BookEntity bookEntity){
        return BookBorrowHistoryResponse.builder()
                .bookResponse(BOOK_MAPPER.buildBookResponse(bookEntity))
                .borrowStatus(bookBorrowHistory.getBorrowStatus().getStatus())
                .borrowDate(bookBorrowHistory.getBorrowDate())
                .returnedDate(bookBorrowHistory.getReturnDate())
                .build();

    }

    public void updateReturnBookBorrowHistory(BookBorrowingEntity bookBorrowHistory, BorrowStatusEntity status){
        bookBorrowHistory.setBorrowStatus(status);
        bookBorrowHistory.setReturnDate(LocalDate.now());
    }
}
