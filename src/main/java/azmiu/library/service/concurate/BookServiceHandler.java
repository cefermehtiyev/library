package azmiu.library.service.concurate;

import azmiu.library.annotation.Log;
import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.repository.BookRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.mapper.BookMapper;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.specification.BookSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;

@Log
@Slf4j
@Service
public class BookServiceHandler implements BookService {
    private final BookRepository bookRepository;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;

    public BookServiceHandler(BookRepository bookRepository,
                              @Lazy CommonStatusService commonStatusService,
                              @Lazy CommonStatusConfig commonStatusConfig) {
        this.bookRepository = bookRepository;
        this.commonStatusService = commonStatusService;
        this.commonStatusConfig = commonStatusConfig;
    }

    @Override
    @Transactional
    public void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        var bookEntity = BOOK_MAPPER.buildBookEntity(bookRequest, status);
        bookEntity.setBookInventory(bookInventoryEntity);

        bookRepository.save(bookEntity);

    }


    @Override
    public BookResponse getBook(Long id) {
        return BOOK_MAPPER.buildBookResponse(findById(id));
    }

    @Override
    public BookEntity getBookEntity(Long id) {
        return findById(id);
    }

    @Override
    public BookEntity getInActiveBookByCode(String bookCode) {
        return findByBookCodeStatusInActive(bookCode);
    }

    @Override
    public BookEntity getActiveBookByCode(String bookCode){
        return findByBookCodeStatusActive(bookCode);
    }

    @Override
    public void updateBook(Long id, BookRequest bookRequest) {
        var bookEntity = findById(id);
        BOOK_MAPPER.updateBookEntity(bookEntity, bookRequest);
        bookRepository.save(bookEntity);
    }


    @Override
    public void updateBookCategory(Long bookId, Long categoryId) {
        var book = findById(bookId);
        bookRepository.save(book);
    }

    @Override
    public void setBookStatusToInactive(String bookCode) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getInActive());
        updateBookStatus(bookCode,status);
    }

    @Override
    public void setBookStatusToActive(String bookCode) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        updateBookStatus(bookCode,status);
    }

    private void updateBookStatus(String bookCode, CommonStatusEntity status){
        var book = findByBookCode(bookCode);
        BOOK_MAPPER.updateBookStatus(book, status);
        bookRepository.save(book);
    }

    @Override
    public PageableResponse getAllBooks(PageCriteria pageCriteria, BookCriteria bookCriteria) {
        var bookPage = bookRepository.findAll(
                new BookSpecification(bookCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), Sort.by("id").descending())
        );

        return BOOK_MAPPER.pageableBookResponse(bookPage);
    }


    @Override
    public PageableResponse getBooksSorted(String order, PageCriteria pageCriteria) {
        var bookPage = bookRepository.findDistinctBooks(order,
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );
        return BOOK_MAPPER.pageableBookResponse(bookPage);
    }


    @Override
    public void deleteBook(Long id) {
        var bookEntity = findById(id);
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getRemoved());
        bookEntity.setCommonStatus(status);
        bookRepository.save(bookEntity);
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
