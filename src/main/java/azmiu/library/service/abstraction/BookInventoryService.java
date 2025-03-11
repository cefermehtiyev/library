package azmiu.library.service.abstraction;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BookInventoryService {
    void addBookToInventory(BookRequest bookRequest, MultipartFile file, MultipartFile image);

    void updateBookInventoryOnReturn(String title, Integer publicationYear);

    void increaseReadCount(Long inventoryId);

    PageableResponse getBooksSorted(String sortBy, String order, PageCriteria pageCriteria);

    void decreaseBookQuantity(BookEntity bookEntity);
}
