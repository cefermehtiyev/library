package az.ingress.service.abstraction;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.model.request.BookRequest;

public interface BookInventoryService {
    void addBookToInventory(BookRequest bookRequest);

    void decreaseBookQuantity(BookEntity bookEntity);
}
