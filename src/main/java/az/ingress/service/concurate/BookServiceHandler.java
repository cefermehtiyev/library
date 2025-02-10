package az.ingress.service.concurate;

import az.ingress.criteria.BookCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookInventoryEntity;
import az.ingress.dao.repository.BookRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.enums.BookStatus;
import az.ingress.model.enums.UserRole;
import az.ingress.model.request.BookRequest;
import az.ingress.model.request.BorrowRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.AuthorService;
import az.ingress.service.abstraction.BookInventoryService;
import az.ingress.service.abstraction.BookBorrowHistoryService;
import az.ingress.service.abstraction.BookService;
import az.ingress.service.abstraction.CategoryService;
import az.ingress.service.abstraction.EmployeeService;
import az.ingress.service.abstraction.FileService;
import az.ingress.service.abstraction.StudentService;
import az.ingress.service.abstraction.UserService;
import az.ingress.service.specification.BookSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static az.ingress.mapper.BookMapper.BOOK_MAPPER;

@Slf4j
@Service
public class BookServiceHandler implements BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final FileService fileService;
    private final StudentService studentService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final BookInventoryService bookInventoryService;
    private final BookBorrowHistoryService bookBorrowHistoryService;

    public BookServiceHandler(BookRepository bookRepository,
                              @Lazy CategoryService categoryService,
                              @Lazy AuthorService authorService,
                              @Lazy FileService fileService,
                              @Lazy StudentService studentService,
                              @Lazy BookInventoryService bookInventoryService,
                              @Lazy BookBorrowHistoryService bookBorrowHistoryService,
                              @Lazy UserService userService,
                              @Lazy EmployeeService employeeService
    ) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.fileService = fileService;
        this.studentService = studentService;
        this.bookInventoryService = bookInventoryService;
        this.bookBorrowHistoryService = bookBorrowHistoryService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public void addBook(BookRequest bookRequest, BookInventoryEntity bookInventoryEntity) {
        var bookEntity = BOOK_MAPPER.buildBookEntity(bookRequest);
        addBookRelationships(bookRequest, bookEntity);
        bookEntity.setBookInventoryEntity(bookInventoryEntity);

        bookRepository.save(bookEntity);
        fileService.uploadFile(bookRequest.getFile(), bookEntity);
        fileService.uploadFile(bookRequest.getImage(), bookEntity);

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
    public void processBookReturn(String fin, String bookCode) {
        var book = fetchEntityExist(bookCode);
        try {
            var student = studentService.getStudentEntityByFin(fin);
            bookInventoryService.updateBookInventoryOnReturn(book.getTitle(), book.getPublicationYear());
            bookBorrowHistoryService.returnBookHistory(student.getId(), book.getId());

        } catch (NotFoundException ex) {
            var employee = employeeService.getEmployeeEntity(fin);
            bookInventoryService.updateBookInventoryOnReturn(book.getTitle(), book.getPublicationYear());
            bookBorrowHistoryService.returnBookHistory(employee.getId(), book.getId());
        }

    }

    @Override
    @Transactional
    public void borrowBook(BorrowRequest borrowRequest) {
        var book = fetchEntityExist(borrowRequest.getBookCode());

        try {
            var student = studentService.getStudentEntityByFin(borrowRequest.getFin());
            student.getBookEntities().add(book);
            bookBorrowHistoryService.addBookToBorrowHistory(student.getUser(), book);

        } catch (NotFoundException ex) {
            var employee = employeeService.getEmployeeEntity(borrowRequest.getFin());
            employee.getBookEntities().add(book);
            bookBorrowHistoryService.addBookToBorrowHistory(employee.getUser(), book);
        }

        bookInventoryService.decreaseBookQuantity(book);
        bookInventoryService.increaseReadCount(book.getBookInventoryEntity().getId());
    }

    @Override
    public void updateBook(Long id, BookRequest bookRequest) {
        var bookEntity = fetchEntityExist(id);
        BOOK_MAPPER.updateBookEntity(bookEntity, bookRequest);
        bookRepository.save(bookEntity);
    }

    @Override
    public List<BookResponse> getAllBooksByFin(String fin) {
        var student = studentService.getStudentEntityByFin(fin);
        return student.getBookEntities().stream().map(BOOK_MAPPER::buildBookResponse).toList();
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
    public PageableResponse getBooksSortedByReadCount(PageCriteria pageCriteria) {
        var bookPage = bookRepository.findDistinctBooksByReadCount(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()));
        return BOOK_MAPPER.pageableBookResponse(bookPage);
    }

    @Override
    public PageableResponse getBooksSortedByPagesDesc(PageCriteria pageCriteria) {
        var bookPage = bookRepository.findDistinctBooksByPagesDesc(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );
        return BOOK_MAPPER.pageableBookResponse(bookPage);
    }

    @Override
    public PageableResponse getBooksSortedByPagesAsc(PageCriteria pageCriteria) {
        var bookPage = bookRepository.findDistinctBooksByPagesAsc(
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );

        return BOOK_MAPPER.pageableBookResponse(bookPage);
    }


    @Override
    public void deleteBook(Long id) {
        var bookEntity = fetchEntityExist(id);
        bookEntity.setBookStatus(BookStatus.DELETED);
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
