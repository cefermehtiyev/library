package azmiu.library.service.concurate;

import azmiu.library.configuration.InventoryStatusConfig;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.repository.BookInventoryRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookInventoryResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.BookService;
import azmiu.library.service.abstraction.FileService;
import azmiu.library.service.abstraction.InventoryStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static azmiu.library.mapper.BookInventoryMapper.BOOK_INVENTORY_MAPPER;
import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;

@Slf4j
@Service
public class BookInventoryServiceHandler implements BookInventoryService {

    private final BookInventoryRepository bookInventoryRepository;
    private final BookService bookService;

    private final InventoryStatusService inventoryStatusService;
    private final InventoryStatusConfig inventoryStatusConfig;
    private final FileService fileService;

    public BookInventoryServiceHandler(BookInventoryRepository bookInventoryRepository,
                                       @Lazy BookService bookService,

                                       @Lazy InventoryStatusService inventoryStatusService,
                                       @Lazy InventoryStatusConfig inventoryStatusConfig,
                                       @Lazy FileService fileService) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookService = bookService;

        this.inventoryStatusService = inventoryStatusService;
        this.inventoryStatusConfig = inventoryStatusConfig;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public void addBookToInventory(BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        var bookInventoryEntity = bookInventoryRepository.findByTitleAndPublicationYear(bookRequest.getTitle(), bookRequest.getPublicationYear())
                .map(existingInventory -> {
                    log.info("Inventory updated");

                    return BOOK_INVENTORY_MAPPER.updateBookInventoryEntity(existingInventory);
                })
                .orElseGet(() -> {
                    var status = inventoryStatusService.getInventoryEntityStatus(inventoryStatusConfig.getLowStock());
                    log.info("Inventory added");
                    var newInventory = BOOK_INVENTORY_MAPPER.buildBookInventoryEntity(bookRequest.getTitle(), bookRequest.getPublicationYear(), status);
                    bookInventoryRepository.save(newInventory);

                    fileService.uploadFile(newInventory, file);
                    fileService.uploadImage(newInventory, image);
                    return newInventory;
                });

        determineInventoryStatus(bookInventoryEntity);

        bookService.addBook(bookRequest, bookInventoryEntity);
    }

    @Override
    public BookInventoryResponse getBookInventory(Long inventoryId) {
        var bookInventoryEntity = getBookInventoryEntity(inventoryId);
        return BOOK_INVENTORY_MAPPER.buildBookInventoryResponse(bookInventoryEntity);
    }


    private void determineInventoryStatus(BookInventoryEntity bookInventoryEntity) {
        var quantity = bookInventoryEntity.getAvailableQuantity();

        var statusId = quantity == 0 ? inventoryStatusConfig.getStockOut() :
                quantity < 3 ? inventoryStatusConfig.getLowStock() :
                        inventoryStatusConfig.getStockOut();

        bookInventoryEntity.setInventoryStatus(inventoryStatusService.getInventoryEntityStatus(statusId));

    }


    @Override
    public void updateBookInventoryOnReturn(String title, Integer publicationYear) {
        var bookInventory = getBookInventoryEntity(title, publicationYear);
        BOOK_INVENTORY_MAPPER.updateInventoryOnReturn(bookInventory);
        determineInventoryStatus(bookInventory);
        bookInventoryRepository.save(bookInventory);
    }

    public void increaseReadCount(Long inventoryId) {
        var bookInventory = getBookInventoryEntity(inventoryId);
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
        var bookInventoryEntity = bookEntity.getBookInventory();

        if (bookInventoryEntity.getAvailableQuantity() <= 0) {
            throw new NotFoundException(ErrorMessage.OUT_OF_STOCK.getMessage());
        }

        BOOK_INVENTORY_MAPPER.decreaseAvailableIncreaseBorrowed(bookInventoryEntity);
        determineInventoryStatus(bookInventoryEntity);
        bookInventoryRepository.save(bookInventoryEntity);

    }


    private BookInventoryEntity getBookInventoryEntity(String title, Integer publicationYear) {
        return bookInventoryRepository.findByTitleAndPublicationYear(title, publicationYear)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_INVENTORY_NOT_FOUND.getMessage())
                );
    }

    public BookInventoryEntity getBookInventoryEntity(Long inventoryId) {
        return bookInventoryRepository.findById(inventoryId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_INVENTORY_NOT_FOUND.getMessage())
                );
    }


}
