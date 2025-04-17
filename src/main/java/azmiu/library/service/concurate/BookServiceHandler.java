package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.repository.BookRepository;
import azmiu.library.exception.AlreadyExistsException;
import azmiu.library.exception.BookCurrentlyBorrowedException;
import azmiu.library.exception.DataMismatchException;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookBorrowingService;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.BookService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.specification.BookSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;


@Slf4j
@Service
public class BookServiceHandler implements BookService {
    private final BookRepository bookRepository;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;
    private final BookInventoryService bookInventoryService;
    private final BookBorrowingService bookBorrowingService;

    public BookServiceHandler(BookRepository bookRepository,
                              @Lazy CommonStatusService commonStatusService,
                              @Lazy CommonStatusConfig commonStatusConfig,
                              @Lazy BookInventoryService bookInventoryService,
                              @Lazy BookBorrowingService bookBorrowingService
    ) {
        this.bookRepository = bookRepository;
        this.commonStatusService = commonStatusService;
        this.commonStatusConfig = commonStatusConfig;
        this.bookInventoryService = bookInventoryService;
        this.bookBorrowingService = bookBorrowingService;
    }

    @Override
    public void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        var bookEntity = BOOK_MAPPER.buildBookEntity(bookRequest, status);
        bookEntity.setBookInventory(bookInventoryEntity);
        bookRepository.save(bookEntity);
    }


    @Override
    public List<BookInventoryEntity> test(String title, String author, Integer publicationYear) {
        var data  = findInventoryByBookDetails(title,author,publicationYear);
        System.out.println(data);
        return null;
    }


    @Override
    public BookResponse getBook(Long id) {
        return BOOK_MAPPER.buildBookResponse(findById(id));
    }

    @Override
    public BookEntity getInActiveBookByCode(String bookCode) {
        return findByBookCodeStatusInActive(bookCode);
    }

    @Override
    public BookEntity getActiveBookByCode(String bookCode) {
        return findByBookCodeStatusActive(bookCode);
    }

    @Override
    public boolean existsByBookCode(String bookCode) {
        return bookRepository.existsByBookCode(bookCode);
    }


    @Override
    @Transactional
    public void updateAllInstancesForBook(Long id, BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        var bookEntity = findById(id);
        bookEntity.setBookCode(bookRequest.getBookCode());
        bookInventoryService.updateBooksInInventory(bookEntity.getBookInventory(), bookRequest, file, image);
        bookRepository.save(bookEntity);
    }

    @Override
    @Transactional
    public void updateSingleBookInstance(Long id, BookRequest bookRequest, MultipartFile file, MultipartFile image) {
        var bookEntity = findById(id);
        if(!bookEntity.getBookCode().equals(bookRequest.getBookCode())){
            if (bookRepository.existsByBookCode(bookRequest.getBookCode())){
                throw new AlreadyExistsException(ErrorMessage.BOOK_CODE_ALREADY_EXISTS.getMessage());
            }
        }
        bookEntity.setBookCode(bookRequest.getBookCode());
        bookInventoryService.updateSingleBookInInventory(bookEntity, bookRequest, file, image);
    }


    @Override
    public void setBookStatusToInactive(String bookCode) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getInActive());
        updateBookStatus(bookCode, status);
    }

    @Override
    public Optional<BookInventoryEntity> findInventoryByBookDetails(String title, String author ,Integer publicationYear) {
        return bookRepository.findFirstByTitleAndAuthorAndPublicationYear(title, author,publicationYear);
    }

    @Override
    public void setBookStatusToActive(String bookCode) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        updateBookStatus(bookCode, status);
    }

    private void updateBookStatus(String bookCode, CommonStatusEntity status) {
        var book = findByBookCode(bookCode);
        BOOK_MAPPER.updateBookStatus(book, status);
        bookRepository.save(book);
    }

    @Override
    public PageableResponse<BookResponse> getAllBooks(String sortBy, String order, PageCriteria pageCriteria, BookCriteria bookCriteria) {
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        var bookPage = bookRepository.findAll(
                new BookSpecification(bookCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), sort)
        );
        return BOOK_MAPPER.pageableBookResponse(bookPage);
    }


    @Override
    @Transactional
    public void deleteBook(Long id) {
        var bookEntity = findById(id);
        var bookInventory = bookEntity.getBookInventory();
        if (bookBorrowingService.isBookBorrowed(id)) {
            throw new BookCurrentlyBorrowedException(ErrorMessage.BOOK_CURRENTLY_BORROWED_EXCEPTION.getMessage());
        }
        bookRepository.delete(bookEntity);
        bookInventoryService.updateCountsOnBookDeleted(bookInventory);
    }

    public BookEntity findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND.getMessage())
        );
    }

    private BookEntity findByBookCodeStatusActive(String bookCode) {
        return bookRepository.findActiveBookByBookCode(bookCode).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND.getMessage())
        );
    }

    private BookEntity findByBookCodeStatusInActive(String bookCode) {
        return bookRepository.findInActiveBookByBookCode(bookCode).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND.getMessage())
        );
    }

    private BookEntity findByBookCode(String bookCode) {
        return bookRepository.findByBookCode(bookCode).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND.getMessage())
        );
    }


}
