package azmiu.library.service.abstraction;

import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity);

    List<BookInventoryEntity> test(String title, String author,Integer publicationYear);

    BookResponse getBook(Long id);

    BookEntity getInActiveBookByCode(String bookCode);

    BookEntity getActiveBookByCode(String bookCode);

    boolean existsByBookCode(String bookCode);

    void setBookStatusToInactive(String bookCode);

    Optional<BookInventoryEntity> findInventoryByBookDetails(String title, String author,Integer publicationYear);

    void setBookStatusToActive(String bookCode);

    void updateAllInstancesForBook(Long id, BookRequest bookRequest, MultipartFile file, MultipartFile image);

    void updateSingleBookInstance(Long id, BookRequest bookRequest, MultipartFile file, MultipartFile image);

    PageableResponse<BookResponse> getAllBooks(String sortBy, String order, PageCriteria pageCriteria, BookCriteria bookCriteria);

    void deleteBook(Long id);

    BookEntity findById(Long id);
}
