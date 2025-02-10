package az.ingress.service.concurate;

import az.ingress.dao.entity.BookBorrowHistoryEntity;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.StudentEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.BookBorrowHistoryRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.BookBorrowHistoryMapper;
import az.ingress.model.response.BookBorrowHistoryResponse;
import az.ingress.service.abstraction.BookBorrowHistoryService;
import az.ingress.service.abstraction.StudentService;
import az.ingress.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.mapper.BookBorrowHistoryMapper.BOOK_LOAN_HISTORY_MAPPER;

@Service
@RequiredArgsConstructor
public class BookBorrowHistoryServiceHandler implements BookBorrowHistoryService {
    private final BookBorrowHistoryRepository bookBorrowHistoryRepository;
    private final UserService userService;

    @Override
    public  void addBookToBorrowHistory(UserEntity userEntity, BookEntity bookEntity) {
        bookBorrowHistoryRepository.save(BOOK_LOAN_HISTORY_MAPPER.buildBookBorrowHistoryEntity(userEntity, bookEntity));
    }



    @Override
    public void returnBookHistory(Long studentId, Long bookId) {
        var bookBorrowHistory = fetchEntityExist(studentId, bookId);
        BOOK_LOAN_HISTORY_MAPPER.updateReturnBookBorrowHistory(bookBorrowHistory);
        bookBorrowHistoryRepository.save(bookBorrowHistory);
    }


    public List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long userId) {
        var user = userService.getUserEntity(userId);

        return user.getBookBorrowHistoryEntity().stream()
                .map(bookBorrowHistory -> {
                    var bookEntity = bookBorrowHistory.getBook();
                    return BOOK_LOAN_HISTORY_MAPPER.buildBookBorrowHistoryResponse(bookBorrowHistory, bookEntity);
                })
                .toList();
    }


    private BookBorrowHistoryEntity fetchEntityExist(Long studentId, Long bookId) {
        return bookBorrowHistoryRepository.findByBookIdAndUserId(studentId, bookId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_BORROW_NOT_FOUND.getMessage())
                );
    }


}
