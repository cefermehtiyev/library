package azmiu.library.service.abstraction;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.BorrowRequest;
import azmiu.library.model.request.ReturnRequest;
import azmiu.library.model.response.BookBorrowHistoryResponse;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;

import java.util.List;

public interface BookBorrowingService {
    void addBookToBorrowHistory(UserEntity userEntity, BookEntity bookEntity);

    PageableResponse<BookResponse> getAllBooksByFin(String fin, PageCriteria pageCriteria);

    void processBookReturn(ReturnRequest returnRequest);

    void borrowBook(BorrowRequest borrowRequest);

    boolean isBookBorrowed(Long bookId);

    List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long studentId);
}
