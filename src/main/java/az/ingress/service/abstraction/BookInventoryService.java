package az.ingress.service.abstraction;

import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.model.request.BookRequest;
import az.ingress.model.response.PageableResponse;

import java.math.BigDecimal;
import java.util.List;

public interface BookInventoryService {
    void addBookToInventory(BookRequest bookRequest);

    void updateBookInventoryOnReturn(String title, Integer publicationYear);

    void increaseReadCount(Long inventoryId);
    void updateBookSize(Long inventoryId, BigDecimal size);

    List<BookInventoryEntity> getAllBookInventoryEntity();

    void decreaseBookQuantity(BookEntity bookEntity);
}
