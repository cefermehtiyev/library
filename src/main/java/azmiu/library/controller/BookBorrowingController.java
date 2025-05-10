package azmiu.library.controller;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.model.request.BorrowRequest;
import azmiu.library.model.request.ReturnRequest;
import azmiu.library.model.response.BookBorrowHistoryResponse;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookBorrowingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/borrow/history")
@FieldDefaults(makeFinal = true,level = PRIVATE)
public class BookBorrowingController {
    BookBorrowingService bookBorrowingService;

    @PostMapping("/add")
    @ResponseStatus(NO_CONTENT)
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void borrowBook(@RequestBody @Valid BorrowRequest borrowRequest) {
        bookBorrowingService.borrowBook(borrowRequest);
    }

    @PostMapping("/return")
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void processBookReturn(@RequestBody @Valid ReturnRequest returnRequest) {
        bookBorrowingService.processBookReturn(returnRequest);
    }

    @GetMapping("/{userId}")
    public List<BookBorrowHistoryResponse> getBooksHistoryByStudent(@PathVariable Long userId) {
        return bookBorrowingService.getBorrowedBooksByStudent(userId);
    }

    @GetMapping("/by-fin")
    public PageableResponse<BookResponse> getAllBooksByFin(@RequestParam String fin, PageCriteria pageCriteria) {
        return bookBorrowingService.getAllBooksByFin(fin, pageCriteria);
    }


}
