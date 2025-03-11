package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.BorrowRequest;
import azmiu.library.model.response.BookBorrowHistoryResponse;

import java.util.List;

public interface BookBorrowingService {
    void addBookToBorrowHistory(UserEntity userEntity, BookEntity bookEntity);


    void processBookReturn(BorrowRequest borrowRequest);

    void borrowBook(BorrowRequest borrowRequest);

    List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long studentId);
}
