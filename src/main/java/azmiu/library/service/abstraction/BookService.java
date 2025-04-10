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
    void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity);

    BookResponse getBook(Long id);

    BookEntity getInActiveBookByCode(String bookCode);

    BookEntity getActiveBookByCode(String bookCode);

    boolean existsByBookCode(String bookCode);

    void setBookStatusToInactive(String bookCode);

    void setBookStatusToActive(String bookCode);

    void updateBook(Long id, BookRequest bookRequest, MultipartFile file, MultipartFile image);

    PageableResponse<BookResponse> getAllBooks(String sortBy, String order, PageCriteria pageCriteria, BookCriteria bookCriteria);


    void deleteBook(Long id);

    BookEntity findById(Long id);
}
