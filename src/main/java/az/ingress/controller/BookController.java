package az.ingress.controller;

import az.ingress.criteria.BookCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.dao.repository.BookRepository;
import az.ingress.model.request.BookRequest;
import az.ingress.model.request.BorrowRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.BookService;
import az.ingress.service.abstraction.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/books")
public class BookController {
    private final BookService bookService;
    private final FileService fileService;
    private final BookRepository bookRepository;


    @PostMapping("/borrow")
    @ResponseStatus(NO_CONTENT)
    public void borrowBook(@RequestBody BorrowRequest borrowRequest) {
        bookService.borrowBook(borrowRequest);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/upload-file")
    public ResponseEntity<InputStreamResource> uploadBook(@RequestParam Long id) {
        return fileService.downloadFile(id);
    }

    @GetMapping("/upload-image")
    public ResponseEntity<InputStreamResource> uploadImage(@RequestParam Long id) {
        return fileService.downloadFile(id);
    }


    @PostMapping("/return")
    public void processBookReturn(@RequestParam String fin, @RequestParam String bookCode) {
        bookService.processBookReturn(fin, bookCode);
    }

    @PutMapping("/update-category")
    public void updateBookCategory(@RequestParam Long bookId, @RequestParam Long categoryId) {
        bookService.updateBookCategory(bookId, categoryId);
    }


    @GetMapping("/by-fin")
    public List<BookResponse> getAllBooksByFin(@RequestParam String fin) {
        return bookService.getAllBooksByFin(fin);
    }

    @GetMapping
    public PageableResponse getAllBooks(PageCriteria pageCriteria, BookCriteria bookCriteria) {
        return bookService.getAllBooks(pageCriteria, bookCriteria);
    }

    @GetMapping("/sorted-by-read-count")
    public PageableResponse getBooksSortedByReadCount(PageCriteria pageCriteria) {
        return bookService.getBooksSortedByReadCount(pageCriteria);
    }

    @GetMapping("/sorted-by-pages-desc")
    public PageableResponse getBooksSortedByPagesDesc(PageCriteria pageCriteria) {
        return bookService.getBooksSortedByPagesDesc(pageCriteria);
    }

    @GetMapping("/sorted-by-pages-asc")
    public PageableResponse getBooksSortedByPagesAsc(PageCriteria pageCriteria) {
        return bookService.getBooksSortedByPagesAsc(pageCriteria);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
