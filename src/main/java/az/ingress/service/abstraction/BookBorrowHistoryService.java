package az.ingress.service.abstraction;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.StudentEntity;
import az.ingress.model.response.BookBorrowHistoryResponse;

import java.util.List;

public interface BookBorrowHistoryService {
    void addBookToBorrowHistory(StudentEntity studentEntity, BookEntity bookEntity);
    void returnBookHistory(Long studentId, Long bookId);
    List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long studentId);
}
