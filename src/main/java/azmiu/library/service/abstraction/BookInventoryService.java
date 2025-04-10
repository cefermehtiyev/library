package azmiu.library.service.abstraction;

import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookInventoryResponse;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BookInventoryService {
    void addBookToInventory(BookRequest bookRequest, MultipartFile file, MultipartFile image);

    BookInventoryResponse getBookInventory(Long inventoryId);

    BookInventoryEntity getBookInventoryEntity(Long inventoryId);

    void updateCountsOnBookDeleted(BookInventoryEntity bookInventoryEntity);

    void updateBookInventoryOnReturn(BookEntity bookEntity);

    void updateBooksInInventory(BookInventoryEntity bookInventory, BookRequest bookRequest, MultipartFile file, MultipartFile image);

    void increaseReadCount(Long inventoryId);


    PageableResponse<BookResponse> getAllBooks(String sortBy, String order, PageCriteria pageCriteria, BookCriteria bookCriteria);

    void decreaseBookQuantity(BookEntity bookEntity);
}
