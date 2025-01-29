package az.ingress.service.concurate;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.BookBorrowHistoryEntity;
import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.repository.BookBorrowHistoryRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.BookBorrowHistoryMapper;
import az.ingress.model.response.BookBorrowHistoryResponse;
import az.ingress.service.abstraction.BookBorrowHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.exception.ErrorMessage.BOOK_BORROW_NOT_FOUND;
import static az.ingress.mapper.BookBorrowHistoryMapper.BOOK_LOAN_HISTORY_MAPPER;

@Service
@RequiredArgsConstructor
public class BookBorrowHistoryServiceHandler implements BookBorrowHistoryService {
    private final BookBorrowHistoryRepository bookBorrowHistoryRepository;

    public void addBookToBorrowHistory(StudentEntity studentEntity, BookEntity bookEntity) {
        bookBorrowHistoryRepository.save(BOOK_LOAN_HISTORY_MAPPER.buildBookLoanHistoryEntity(studentEntity, bookEntity));
    }

    public List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long studentId) {
        var bookBorrowHistory = fetchEntityExist(studentId);
        return bookBorrowHistory.getStudent().getBookEntities().stream()
                .map(bookEntity -> BOOK_LOAN_HISTORY_MAPPER.buildBookLoanHistoryResponse(bookBorrowHistory, bookEntity))
                .toList();
    }

    private BookBorrowHistoryEntity fetchEntityExist(Long studentId) {
        return bookBorrowHistoryRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException(BOOK_BORROW_NOT_FOUND.getMessage())
        );
    }
}
