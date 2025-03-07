package azmiu.library.service.abstraction;

import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Long addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity);

    BookResponse getBook(Long id);

    BookEntity getBookEntityByBookCode(String bookCode);

    void updateBookCategory(Long bookId, Long categoryId);

    PageableResponse getAllBooks(PageCriteria pageCriteria, BookCriteria bookCriteria);

    PageableResponse getBooksSorted(String order,PageCriteria pageCriteria);

    void updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

    BookEntity fetchEntityExist(Long id);
}
