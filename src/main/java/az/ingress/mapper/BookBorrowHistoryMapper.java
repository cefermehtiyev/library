package az.ingress.mapper;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookBorrowHistoryEntity;
import az.ingress.dao.entity.StudentEntity;
import az.ingress.model.enums.BorrowStatus;
import az.ingress.model.response.BookBorrowHistoryResponse;

import static az.ingress.mapper.BookMapper.BOOK_MAPPER;

public enum BookBorrowHistoryMapper {
    BOOK_LOAN_HISTORY_MAPPER;

    public BookBorrowHistoryEntity buildBookLoanHistoryEntity(StudentEntity student, BookEntity book){
        return BookBorrowHistoryEntity
                .builder()
                .book(book)
                .student(student)
                .status(BorrowStatus.DELAYED).build();
    }

    public BookBorrowHistoryResponse buildBookLoanHistoryResponse(BookBorrowHistoryEntity bookBorrowHistory,BookEntity bookEntity){
        return BookBorrowHistoryResponse
                .builder()
                .bookResponse(BOOK_MAPPER.buildBookResponse(bookEntity))
                .borrowStatus(BorrowStatus.DELAYED)
                .borrowDate(bookBorrowHistory.getBorrowDate())
                .returnedDate(bookBorrowHistory.getReturnDate())
                .build();

    }
}
