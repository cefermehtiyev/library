package az.ingress.service.abstraction;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.BorrowRequest;
import az.ingress.model.response.BookBorrowHistoryResponse;

import java.util.List;

public interface BookBorrowHistoryService {
    void addBookToBorrowHistory(UserEntity userEntity, BookEntity bookEntity);

    void returnBookHistory(Long userId, Long bookId);

    void processBookReturn(BorrowRequest borrowRequest);

    void borrowBook(BorrowRequest borrowRequest);

    List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long studentId);
}
