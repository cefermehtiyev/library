package az.ingress.service.concurate;

import az.ingress.configuration.CommonStatusConfig;
import az.ingress.configuration.InventoryStatusConfig;
import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.dao.repository.BookInventoryRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.BookMapper;
import az.ingress.model.request.BookRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.BookInventoryService;
import az.ingress.service.abstraction.BookService;
import az.ingress.service.abstraction.CommonStatusService;
import az.ingress.service.abstraction.InventoryStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import static az.ingress.mapper.BookInventoryMapper.BOOK_INVENTORY_MAPPER;
import static az.ingress.mapper.BookMapper.BOOK_MAPPER;

@Slf4j
@Service
public class BookInventoryServiceHandler implements BookInventoryService {

    private final BookInventoryRepository bookInventoryRepository;
    private final BookService bookService;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;
    private final InventoryStatusService inventoryStatusService;
    private final InventoryStatusConfig inventoryStatusConfig;

    public BookInventoryServiceHandler(BookInventoryRepository bookInventoryRepository,
                                       @Lazy BookService bookService,
                                       @Lazy CommonStatusService commonStatusService,
                                       @Lazy CommonStatusConfig commonStatusConfig,
                                       @Lazy InventoryStatusService inventoryStatusService,
                                       @Lazy InventoryStatusConfig inventoryStatusConfig
    ) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookService = bookService;
        this.commonStatusService = commonStatusService;
        this.commonStatusConfig = commonStatusConfig;
        this.inventoryStatusService = inventoryStatusService;
        this.inventoryStatusConfig = inventoryStatusConfig;
    }

    @Override
    @Transactional
    public void addBookToInventory(BookRequest bookRequest) {
        var bookInventoryEntity = bookInventoryRepository.findByTitleAndPublicationYear(bookRequest.getTitle(), bookRequest.getPublicationYear())
                .map(existingInventory -> {
                    log.info("Inventory updated");
                    return BOOK_INVENTORY_MAPPER.updateBookInventoryEntity(existingInventory);
                })
                .orElseGet(() -> {
                    var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
                    log.info("Inventory added");
                    return BOOK_INVENTORY_MAPPER.buildBookInventoryEntity(bookRequest.getTitle(), bookRequest.getPublicationYear(), status);
                });

        determineInventoryStatus(bookInventoryEntity);
        bookInventoryRepository.save(bookInventoryEntity);
        bookService.addBook(bookRequest, bookInventoryEntity);
    }

    private void determineInventoryStatus(BookInventoryEntity bookInventoryEntity) {
        var quantity = bookInventoryEntity.getAvailableQuantity();

        var statusId = quantity == 0 ? inventoryStatusConfig.getStockOut() :
                quantity < 3 ? inventoryStatusConfig.getLowStock() :
                        inventoryStatusConfig.getStockOut();

        bookInventoryEntity.setInventoryStatus(inventoryStatusService.getInventoryEntity(statusId));

    }


    @Override
    public void updateBookInventoryOnReturn(String title, Integer publicationYear) {
        var bookInventory = fetchEntityExist(title, publicationYear);
        BOOK_INVENTORY_MAPPER.updateInventoryOnReturn(bookInventory);
        determineInventoryStatus(bookInventory);
        bookInventoryRepository.save(bookInventory);
    }

    public void increaseReadCount(Long inventoryId) {
        var bookInventory = fetchEntityExist(inventoryId);
        BOOK_INVENTORY_MAPPER.increaseReadCount(bookInventory);
        bookInventoryRepository.save(bookInventory);
    }


    @Override
    public PageableResponse getBooksSorted(String sortBy, String order, PageCriteria pageCriteria) {

        var page = bookInventoryRepository.findDistinctBooks(sortBy, order,
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );

        return BOOK_MAPPER.pageableBookResponse(page);

    }


    @Override
    public void decreaseBookQuantity(BookEntity bookEntity) {
        var bookInventoryEntity = bookEntity.getBookInventoryEntity();

        if (bookInventoryEntity.getAvailableQuantity() <= 0) {
            throw new NotFoundException(ErrorMessage.OUT_OF_STOCK.getMessage());
        }

        BOOK_INVENTORY_MAPPER.decreaseAvailableIncreaseBorrowed(bookInventoryEntity);
        determineInventoryStatus(bookInventoryEntity);
        bookInventoryRepository.save(bookInventoryEntity);

    }


    private BookInventoryEntity fetchEntityExist(String title, Integer publicationYear) {
        return bookInventoryRepository.findByTitleAndPublicationYear(title, publicationYear)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_INVENTORY_NOT_FOUND.getMessage())
                );
    }

    private BookInventoryEntity fetchEntityExist(Long inventoryId) {
        return bookInventoryRepository.findById(inventoryId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_INVENTORY_NOT_FOUND.getMessage())
                );
    }


}
