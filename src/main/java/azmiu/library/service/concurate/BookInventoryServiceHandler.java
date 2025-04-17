package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.configuration.InventoryStatusConfig;
import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.repository.BookInventoryRepository;
import azmiu.library.dao.repository.BookRepository;
import azmiu.library.exception.AlreadyExistsException;
import azmiu.library.exception.DataMismatchException;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.InvalidUpdateException;
import azmiu.library.exception.NotFoundException;
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
import azmiu.library.service.abstraction.ImageService;
import azmiu.library.service.abstraction.InventoryStatusService;
import azmiu.library.service.abstraction.RatingDetailsService;
import azmiu.library.service.specification.BookInventorySpecification;
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
    private final ImageService imageService;


    public BookInventoryServiceHandler(BookInventoryRepository bookInventoryRepository,
                                       @Lazy BookService bookService,
                                       @Lazy InventoryStatusService inventoryStatusService,
                                       @Lazy InventoryStatusConfig inventoryStatusConfig,
                                       @Lazy FileService fileService,
                                       @Lazy CategoryService categoryService,
                                       @Lazy CommonStatusService commonStatusService,
                                       @Lazy CommonStatusConfig commonStatusConfig,
                                       @Lazy RatingDetailsService ratingDetailsService,
                                       @Lazy ImageService imageService) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookService = bookService;
        this.inventoryStatusService = inventoryStatusService;
        this.inventoryStatusConfig = inventoryStatusConfig;
        this.fileService = fileService;
        this.categoryService = categoryService;
        this.commonStatusService = commonStatusService;
        this.commonStatusConfig = commonStatusConfig;
        this.ratingDetailsService = ratingDetailsService;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public void addBookToInventory(BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        if (bookService.existsByBookCode(bookRequest.getBookCode())) {
            throw new AlreadyExistsException(ErrorMessage.BOOK_ALREADY_EXISTS.getMessage());
        }

        var bookInventoryEntity = bookService.findInventoryByBookDetails(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getPublicationYear())
                .map(existingInventory -> {
                    log.info("Inventory updated");
                    if (!validateBookDataConsistency(existingInventory.getBooks().getFirst(), bookRequest)) {
                        throw new DataMismatchException(ErrorMessage.BOOK_DATA_MISMATCH_EXCEPTION.getMessage());
                    }
                    return BOOK_INVENTORY_MAPPER.increaseBookInventoryQuantities(existingInventory);
                })
                .orElseGet(() -> createNewBookInventory(bookRequest, file, image));

        determineInventoryStatus(bookInventoryEntity);
        bookService.addBook(bookRequest, bookInventoryEntity);
    }


    private BookInventoryEntity createNewBookInventory(BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        var inventoryStatus = inventoryStatusService.getInventoryEntityStatus(inventoryStatusConfig.getLowStock());
        var commonStatus = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        var fileEntity = fileService.uploadFile(file);
        var imageEntity = imageService.uploadImage(image);
        log.info("Inventory added");
        var newInventory = BOOK_INVENTORY_MAPPER
                .buildBookInventoryEntity(fileEntity, imageEntity, bookRequest.getTitle(), bookRequest.getPublicationYear(), inventoryStatus, commonStatus);
        categoryService.addBookToCategory(bookRequest.getCategoryId(), newInventory);
        bookInventoryRepository.save(newInventory);
        ratingDetailsService.initializeRatingDetails(newInventory);
        return newInventory;
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
    public void updateCountsOnBookDeleted(BookInventoryEntity bookInventoryEntity) {
        BOOK_INVENTORY_MAPPER.updateCountsOnBookDeleted(bookInventoryEntity);
        determineInventoryStatus(bookInventoryEntity);
        log.info("Book reserved count :{}", bookInventoryEntity.getReservedQuantity());
        System.out.println(bookInventoryEntity.getBooks().getFirst().getBookCode());
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
        updateOrDeleteFile(bookInventory, file);
        updateOrDeleteImage(bookInventory, image);
        var category = categoryService.getCategoryEntity(bookRequest.getCategoryId());
        BOOK_INVENTORY_MAPPER.updateBookInventory(bookInventory, category, bookRequest.getTitle(), bookRequest.getPublicationYear());
        log.info("Book all instances updated in Inventory");
    }

    @Override
    @Transactional
    public void updateSingleBookInInventory(BookEntity bookEntity, BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        var currentInventory = bookEntity.getBookInventory();

        if (isBookEquivalent(bookEntity, bookRequest)) {
            if (validateBookDataConsistency(bookEntity, bookRequest)) {
                log.info("Request data is consistent. Updating book code.");
                bookEntity.setBookCode(bookRequest.getBookCode());
            } else {
                throw new InvalidUpdateException(ErrorMessage.INVALID_BOOK_UPDATE_EXCEPTION.getMessage());
            }
        }else  {
            log.info("Book is not equivalent. Reassigning book to appropriate inventory.");
            reassignBookToFoundInventory(bookEntity, bookRequest, currentInventory, file, image);
        }
    }

    private void reassignBookToFoundInventory(BookEntity bookEntity, BookRequest bookRequest, BookInventoryEntity currentInventory, MultipartFile file, MultipartFile image) {
        var bookInventoryEntity = bookService.findInventoryByBookDetails(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getPublicationYear())
                .map(bookInventory -> {
                    var updatedInventory = BOOK_INVENTORY_MAPPER.increaseBookInventoryQuantities(bookInventory);
                    BOOK_MAPPER.updateBookEntity(bookEntity, bookInventory.getBooks().getFirst());
                    bookEntity.setBookInventory(updatedInventory);
                    return updatedInventory;
                })
                .orElseGet(() ->{
                    var  newInventory = createNewBookInventory(bookRequest, file, image);
                    BOOK_MAPPER.updateBookEntity(bookEntity,bookRequest);
                    bookEntity.setBookInventory(newInventory);
                    return newInventory;});
        determineInventoryStatus(bookInventoryEntity);
        updateInventoryAfterBookRemoval(currentInventory);
        bookInventoryRepository.save(bookInventoryEntity);
    }
    private void updateInventoryAfterBookRemoval(BookInventoryEntity bookInventory){
        updateCountsOnBookDeleted(bookInventory);
        determineInventoryStatus(bookInventory);
    }

    private boolean validateBookDataConsistency(BookEntity bookEntity, BookRequest bookRequest) {
        return bookEntity.getBookInventory().getCategory().getId().equals(bookRequest.getCategoryId()) &&
                bookEntity.getLanguage().equals(bookRequest.getLanguage()) &&
                bookEntity.getPages().equals(bookRequest.getPages()) &&
                bookEntity.getDescription().equals(bookRequest.getDescription()) &&
                bookEntity.getPublisher().equals(bookRequest.getPublisher());
    }

    private boolean isBookEquivalent(BookEntity bookEntity, BookRequest bookRequest) {
        return bookEntity.getTitle().equals(bookRequest.getTitle())
                && bookEntity.getPublicationYear().equals(bookRequest.getPublicationYear())
                && bookEntity.getAuthor().equals(bookRequest.getAuthor());
    }

    private void updateOrDeleteImage(BookInventoryEntity bookInventory, MultipartFile image) {
        if (image.isEmpty()) {
            imageService.deleteImage(bookInventory.getImage().getId());
        } else {
            imageService.updateImage(bookInventory, image);

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
