package az.ingress.service.concurate;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.dao.repository.BookInventoryRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.enums.InventoryStatus;
import az.ingress.model.request.BookRequest;
import az.ingress.service.abstraction.BookInventoryService;
import az.ingress.service.abstraction.BookService;
import az.ingress.service.abstraction.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.BookInventoryMapper.BOOK_INVENTORY_MAPPER;

@Slf4j
@Service
public class BookInventoryServiceHandler implements BookInventoryService {

    private final BookInventoryRepository bookInventoryRepository;
    private final BookService bookService;
    private final CategoryService categoryService;

    public BookInventoryServiceHandler(BookInventoryRepository bookInventoryRepository,
                                       @Lazy BookService bookService,
                                       @Lazy CategoryService categoryService) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void addBookToInventory(BookRequest bookRequest) {
        var bookInventoryEntity = bookInventoryRepository.findByTitleAndPublicationYear(bookRequest.getTitle(), bookRequest.getPublicationYear())
                .map(existingInventory -> {
                    log.info("Inventory updated");
                    return BOOK_INVENTORY_MAPPER.updateBookInventoryEntity(existingInventory);
                })
                .orElseGet(() -> {
                    log.info("Inventory added");
                    return BOOK_INVENTORY_MAPPER.buildBookInventoryEntity(bookRequest.getTitle(), bookRequest.getPublicationYear());
                });
        bookInventoryRepository.save(bookInventoryEntity);
        bookService.addBook(bookRequest, bookInventoryEntity);
    }

    @Override
    public void updateBookInventoryOnReturn(String title, Integer publicationYear) {
        var bookInventory = fetchEntityExist(title, publicationYear);
        BOOK_INVENTORY_MAPPER.updateInventoryOnReturn(bookInventory);
        bookInventoryRepository.save(bookInventory);
    }

    @Override
    public void decreaseBookQuantity(BookEntity bookEntity) {
        var bookInventoryEntity = bookEntity.getBookInventoryEntity();

        if (bookInventoryEntity.getAvailableQuantity() <= 0) {
            throw new NotFoundException(ErrorMessage.OUT_OF_STOCK.getMessage());
        }

        BOOK_INVENTORY_MAPPER.decreaseAvailableIncreaseBorrowed(bookInventoryEntity);

        bookInventoryRepository.save(bookInventoryEntity);

    }

    private BookInventoryEntity fetchEntityExist(String title, Integer publicationYear) {
        return bookInventoryRepository.findByTitleAndPublicationYear(title, publicationYear)
                .orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_INVENTORY_NOT_FOUND.getMessage())
        );
    }


}
