package az.ingress.service.abstraction;

import az.ingress.criteria.BookCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.model.request.BookRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;

import java.util.List;

public interface BookService {
    void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity);

    BookResponse getBook(Long id);

    BookEntity getBookEntityByBookCode(String bookCode);


    void updateBookCategory(Long bookId, Long categoryId);

    PageableResponse getAllBooks(PageCriteria pageCriteria, BookCriteria bookCriteria);

    PageableResponse getBooksSorted(String order,PageCriteria pageCriteria);

    void updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

    BookEntity fetchEntityExist(Long id);
}
