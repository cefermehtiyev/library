package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.configuration.InventoryStatusConfig;
import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.repository.BookInventoryRepository;
import azmiu.library.dao.repository.FileRepository;
import azmiu.library.dao.repository.ImageRepository;
import azmiu.library.dao.repository.SavedBookRepository;
import azmiu.library.exception.AlreadyExistsException;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.mapper.BookInventoryMapper;
import azmiu.library.mapper.BookMapper;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookInventoryResponse;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.BookService;
import azmiu.library.service.abstraction.CategoryService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.abstraction.FileService;
import azmiu.library.service.abstraction.InventoryStatusService;
import azmiu.library.service.abstraction.RatingDetailsService;
import azmiu.library.service.specification.BookInventorySpecification;
import azmiu.library.service.specification.BookSpecification;
import azmiu.library.util.CacheProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
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
    private final CategoryService categoryService;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;
    private final RatingDetailsService ratingDetailsService;


    public BookInventoryServiceHandler(BookInventoryRepository bookInventoryRepository,
                                       @Lazy BookService bookService,
                                       @Lazy InventoryStatusService inventoryStatusService,
                                       @Lazy InventoryStatusConfig inventoryStatusConfig,
                                       @Lazy FileService fileService,
                                       @Lazy CategoryService categoryService,
                                       @Lazy CommonStatusService commonStatusService,
                                       @Lazy CommonStatusConfig commonStatusConfig,
                                       @Lazy RatingDetailsService ratingDetailsService
                                       ) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookService = bookService;
        this.inventoryStatusService = inventoryStatusService;
        this.inventoryStatusConfig = inventoryStatusConfig;
        this.fileService = fileService;
        this.categoryService = categoryService;
        this.commonStatusService = commonStatusService;
        this.commonStatusConfig = commonStatusConfig;
        this.ratingDetailsService = ratingDetailsService;
    }

    @Override
    @Transactional
    public void addBookToInventory(BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        if (bookService.existsByBookCode(bookRequest.getBookCode())) {
            throw new AlreadyExistsException(ErrorMessage.BOOK_ALREADY_EXISTS.getMessage());
        }
        var bookInventoryEntity = bookInventoryRepository.findByTitleAndPublicationYear(bookRequest.getTitle(), bookRequest.getPublicationYear())
                .map(existingInventory -> {
                    log.info("Inventory updated");
                    return BOOK_INVENTORY_MAPPER.increaseBookInventoryQuantities(existingInventory);
                })
                .orElseGet(() -> {
                    var inventoryStatus = inventoryStatusService.getInventoryEntityStatus(inventoryStatusConfig.getLowStock());
                    var commonStatus = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());

                    log.info("Inventory added");
                    var newInventory = BOOK_INVENTORY_MAPPER.buildBookInventoryEntity(bookRequest.getTitle(), bookRequest.getPublicationYear(), inventoryStatus, commonStatus);
                    categoryService.addBookToCategory(bookRequest.getCategoryId(), newInventory);
                    bookInventoryRepository.save(newInventory);

                    fileService.uploadFile(newInventory, file);
                    fileService.uploadImage(newInventory, image);

                    ratingDetailsService.initializeRatingDetails(newInventory);
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
        log.info("Available quantity {}", quantity);

        var statusId = quantity == 0 ? inventoryStatusConfig.getStockOut() :
                quantity < 3 ? inventoryStatusConfig.getLowStock() :
                        inventoryStatusConfig.getInStock();

        bookInventoryEntity.setInventoryStatus(inventoryStatusService.getInventoryEntityStatus(statusId));

    }


    @Override
    public void updateBookInventoryOnReturn(BookEntity bookEntity) {
        var bookInventory = bookEntity.getBookInventory();
        BOOK_INVENTORY_MAPPER.updateInventoryOnReturn(bookInventory);
        determineInventoryStatus(bookInventory);
        bookInventoryRepository.save(bookInventory);
    }

    @Override
    @Transactional
    public void updateCountsOnBookDeleted(BookInventoryEntity bookInventoryEntity) {
        BOOK_INVENTORY_MAPPER.updateCountsOnBookDeleted(bookInventoryEntity);
        determineInventoryStatus(bookInventoryEntity);
        log.info("Book reserved count :{}", bookInventoryEntity.getReservedQuantity());
        if (bookInventoryEntity.getReservedQuantity() == 0) {
            bookInventoryRepository.delete(bookInventoryEntity);
            log.info("Book Deleted");
        }
    }

    public void increaseReadCount(Long inventoryId) {
        var bookInventory = getBookInventoryEntity(inventoryId);
        BOOK_INVENTORY_MAPPER.increaseReadCount(bookInventory);
        bookInventoryRepository.save(bookInventory);
    }


    @Override
    public PageableResponse<BookResponse> getAllBooks(String sortBy, String order, PageCriteria pageCriteria, BookCriteria bookCriteria) {
        Sort.Direction direction = "desc".equals(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        var bookPage = bookInventoryRepository.findAll(
                new BookInventorySpecification(bookCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), sort)
        );

        return BOOK_INVENTORY_MAPPER.pageableBookInventoryResponse(bookPage);
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


    public BookInventoryEntity getBookInventoryEntity(Long inventoryId) {
        return bookInventoryRepository.findById(inventoryId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_INVENTORY_NOT_FOUND.getMessage())
                );
    }

    @Override
    public void updateBooksInInventory(BookInventoryEntity bookInventory, BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        bookInventory.getBooks().forEach(bookEntity -> BOOK_MAPPER.updateBookEntity(bookEntity, bookRequest));
        updateOrDeleteFile(bookInventory,file);
        updateOrDeleteImage(bookInventory,image);
        var category = categoryService.getCategoryEntity(bookRequest.getCategoryId());
        BOOK_INVENTORY_MAPPER.updateBookInventory(bookInventory, category, bookRequest.getTitle(), bookRequest.getPublicationYear());
        log.info("Inventory Updated");
    }

    private void updateOrDeleteImage(BookInventoryEntity bookInventory, MultipartFile image) {
        if (image.isEmpty()) {
            fileService.deleteImage(bookInventory.getId());
        } else {
            fileService.updateImage(bookInventory, image);

        }
    }

    private void updateOrDeleteFile(BookInventoryEntity bookInventory, MultipartFile file) {
        if (file.isEmpty()) {
            fileService.deleteFile(bookInventory.getFile().getId());
        } else {
            fileService.updateFile(bookInventory, file);
        }
    }


}
