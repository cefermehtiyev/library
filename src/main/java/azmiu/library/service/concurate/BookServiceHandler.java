package azmiu.library.service.concurate;

import azmiu.library.annotation.Log;
import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.repository.BookRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.AuthorService;
import azmiu.library.service.abstraction.BookService;
import azmiu.library.service.abstraction.CategoryService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.abstraction.FileService;
import azmiu.library.service.specification.BookSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;

@Log
@Slf4j
@Service
public class BookServiceHandler implements BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final FileService fileService;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;

    public BookServiceHandler(BookRepository bookRepository,
                              @Lazy CategoryService categoryService,
                              @Lazy AuthorService authorService,
                              @Lazy FileService fileService,
                              @Lazy CommonStatusService commonStatusService,
                              @Lazy CommonStatusConfig commonStatusConfig
    ) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.fileService = fileService;
        this.commonStatusService = commonStatusService;
        this.commonStatusConfig = commonStatusConfig;
    }

    @Override
    @Transactional
    public void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity, MultipartFile file, MultipartFile image) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        var bookEntity = BOOK_MAPPER.buildBookEntity(bookRequest, status);
        addBookRelationships(bookRequest, bookEntity);
        bookEntity.setBookInventoryEntity(bookInventoryEntity);

        bookRepository.save(bookEntity);
        fileService.uploadFile(file, bookEntity);
        fileService.uploadFile(image, bookEntity);

    }


    private void addBookRelationships(BookRequest bookRequest, BookEntity bookEntity) {
        authorService.addBookToAuthor(bookEntity);
        categoryService.addBookToCategory(bookRequest.getCategoryId(), bookEntity);

    }


    @Override
    public BookResponse getBook(Long id) {
        return BOOK_MAPPER.buildBookResponse(fetchEntityExist(id));
    }

    @Override
    public BookEntity getBookEntityByBookCode(String bookCode) {
        return fetchEntityExist(bookCode);
    }

    @Override
    public void updateBook(Long id, BookRequest bookRequest) {
        var bookEntity = fetchEntityExist(id);
        BOOK_MAPPER.updateBookEntity(bookEntity, bookRequest);
        bookRepository.save(bookEntity);
    }


    public void updateBookCategory(Long bookId, Long categoryId) {
        var book = fetchEntityExist(bookId);
        categoryService.addBookToCategory(categoryId, book);
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
        var bookEntity = fetchEntityExist(id);
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getRemoved());
        bookEntity.setCommonStatusEntity(status);
        bookRepository.save(bookEntity);
    }

    public BookEntity fetchEntityExist(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND.getMessage())
        );
    }

    private BookEntity fetchEntityExist(String bookCode) {
        return bookRepository.findByBookCode(bookCode).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND.getMessage())
        );
    }
}
