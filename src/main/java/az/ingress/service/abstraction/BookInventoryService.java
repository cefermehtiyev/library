package az.ingress.service.abstraction;

import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.model.request.BookRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;

public interface BookInventoryService {
    void addBookToInventory(BookRequest bookRequest);

    void updateBookInventoryOnReturn(String title, Integer publicationYear);

    void increaseReadCount(Long inventoryId);

    PageableResponse getBooksSorted(String sortBy, String order, PageCriteria pageCriteria);

    void decreaseBookQuantity(BookEntity bookEntity);
}
