package az.ingress.service.abstraction;

import az.ingress.criteria.BookCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.model.request.BookRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;

import java.util.List;

public interface BookService {
    void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity);

    BookResponse getBook(String bookCode);

    void processBookReturn(String studentFin,String bookCode);

    void borrowBook(String studentFin, String bookCode);

    List<BookResponse> getAllBooksByFin(String fin);

    PageableResponse getAllBooks(PageCriteria pageCriteria, BookCriteria bookCriteria);

    void updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);
}
