package az.ingress.controller;

import az.ingress.criteria.BookCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.model.request.BookRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }


    @PutMapping("/update-category")
    public void updateBookCategory(@RequestParam Long bookId, @RequestParam Long categoryId) {
        bookService.updateBookCategory(bookId, categoryId);
    }


    @GetMapping("/sorted")
    public PageableResponse getAllBooks(PageCriteria pageCriteria, BookCriteria bookCriteria) {
        return bookService.getAllBooks(pageCriteria, bookCriteria);
    }


    @GetMapping("/sorted/page")
    public PageableResponse getBooksSorted(@RequestParam(defaultValue = "asc") String order, PageCriteria pageCriteria) {
        return bookService.getBooksSorted(order, pageCriteria);
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
