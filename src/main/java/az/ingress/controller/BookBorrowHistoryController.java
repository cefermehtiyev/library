package az.ingress.controller;

import az.ingress.model.response.BookBorrowHistoryResponse;
import az.ingress.service.abstraction.BookBorrowHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/borrow/history")
public class BookBorrowHistoryController {
    private final BookBorrowHistoryService bookBorrowHistoryService;

    @GetMapping("/{userId}")
    public List<BookBorrowHistoryResponse> getBooksHistoryByStudent(@PathVariable Long userId) {
        return bookBorrowHistoryService.getBorrowedBooksByStudent(userId);
    }
}
