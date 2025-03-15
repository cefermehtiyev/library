package azmiu.library.controller;

import azmiu.library.model.request.BorrowRequest;
import azmiu.library.model.response.BookBorrowHistoryResponse;
import azmiu.library.service.abstraction.BookBorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/borrow/history")
public class BookBorrowingController {
    private final BookBorrowingService bookBorrowingService;

    @PostMapping("/add")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void borrowBook(@RequestBody BorrowRequest borrowRequest) {
        bookBorrowingService.borrowBook(borrowRequest);
    }

    @PostMapping("/return")
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void processBookReturn(@RequestBody BorrowRequest borrowRequest) {
        bookBorrowingService.processBookReturn(borrowRequest);
    }

    @GetMapping("/{userId}")
    public List<BookBorrowHistoryResponse> getBooksHistoryByStudent(@PathVariable Long userId) {
        return bookBorrowingService.getBorrowedBooksByStudent(userId);
    }


}
