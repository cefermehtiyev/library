package az.ingress.mapper;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookBorrowHistoryEntity;
import az.ingress.dao.entity.StudentEntity;
import az.ingress.model.enums.BorrowStatus;
import az.ingress.model.response.BookBorrowHistoryResponse;

import java.time.LocalDate;

import static az.ingress.mapper.BookMapper.BOOK_MAPPER;
import static az.ingress.model.enums.BorrowStatus.DELAYED;
import static az.ingress.model.enums.BorrowStatus.PENDING;
import static az.ingress.model.enums.BorrowStatus.RETURNED;

public enum BookBorrowHistoryMapper {
    BOOK_LOAN_HISTORY_MAPPER;

    public BookBorrowHistoryEntity buildBookBorrowHistoryEntity(StudentEntity student, BookEntity book){
        return BookBorrowHistoryEntity
                .builder()
                .book(book)
                .student(student)
                .status(PENDING).build();
    }

    public BookBorrowHistoryResponse buildBookBorrowHistoryResponse(BookBorrowHistoryEntity bookBorrowHistory, BookEntity bookEntity){
        return BookBorrowHistoryResponse
                .builder()
                .bookResponse(BOOK_MAPPER.buildBookResponse(bookEntity))
                .borrowStatus(bookBorrowHistory.getStatus())
                .borrowDate(bookBorrowHistory.getBorrowDate())
                .returnedDate(bookBorrowHistory.getReturnDate())
                .build();

    }

    public void updateReturnBookBorrowHistory(BookBorrowHistoryEntity bookBorrowHistory){
        bookBorrowHistory.setStatus(RETURNED);
        bookBorrowHistory.setReturnDate(LocalDate.now());
    }
}
