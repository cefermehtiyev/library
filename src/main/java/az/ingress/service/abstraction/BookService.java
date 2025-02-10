package az.ingress.service.abstraction;

import az.ingress.criteria.BookCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.model.request.BookRequest;
import az.ingress.model.request.BorrowRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;

import java.util.List;

public interface BookService {
    void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity);

    BookResponse getBook(Long id);

    void processBookReturn(String fin, String bookCode);

    void borrowBook(BorrowRequest borrowRequest);

    List<BookResponse> getAllBooksByFin(String fin);

    void updateBookCategory(Long bookId, Long categoryId);

    PageableResponse getAllBooks(PageCriteria pageCriteria, BookCriteria bookCriteria);

    PageableResponse getBooksSortedByReadCount(PageCriteria pageCriteria);

    PageableResponse getBooksSortedByPagesDesc(PageCriteria pageCriteria);

    PageableResponse getBooksSortedByPagesAsc(PageCriteria pageCriteria);


    void updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

    BookEntity fetchEntityExist(Long id);
}
