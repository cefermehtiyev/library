package azmiu.library.service.concurate;

import azmiu.library.configuration.BorrowStatusConfig;
import azmiu.library.dao.entity.BookBorrowingEntity;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.BookBorrowHistoryRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.request.BorrowRequest;
import azmiu.library.model.response.BookBorrowHistoryResponse;
import azmiu.library.service.abstraction.BookBorrowingService;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.BookService;
import azmiu.library.service.abstraction.BorrowStatusService;
import azmiu.library.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static azmiu.library.mapper.BookBorrowingMapper.BOOK_LOAN_HISTORY_MAPPER;

@Service
@RequiredArgsConstructor
public class BookBorrowingServiceHandler implements BookBorrowingService {
    private final BookBorrowHistoryRepository bookBorrowHistoryRepository;
    private final UserService userService;
    private final BookInventoryService bookInventoryService;
    private final BookService bookService;
    private final BorrowStatusService borrowStatusService;
    private final BorrowStatusConfig borrowStatusConfig;


    @Override
    public void addBookToBorrowHistory(UserEntity userEntity, BookEntity bookEntity) {
        var status = borrowStatusService.getBorrowStatus(borrowStatusConfig.getPending());
        bookBorrowHistoryRepository.save(BOOK_LOAN_HISTORY_MAPPER.buildBookBorrowHistoryEntity(userEntity, bookEntity, status));
    }


    private void updateBookHistory(Long userId, BookEntity bookEntity) {
        var bookBorrowHistory = fetchEntityExist(userId, bookEntity.getId());
        var status = borrowStatusService.getBorrowStatus(borrowStatusConfig.getReturned());
        BOOK_LOAN_HISTORY_MAPPER.updateReturnBookBorrowHistory(bookBorrowHistory, status);
        bookBorrowHistoryRepository.save(bookBorrowHistory);
        bookInventoryService.updateBookInventoryOnReturn(bookEntity);
    }

    @Override
    public void processBookReturn(BorrowRequest borrowRequest) {
        var bookEntity = bookService.getBookEntityByBookCode(borrowRequest.getBookCode());
        var userEntity = userService.getUserEntityByUserName(borrowRequest.getUserName());
        updateBookHistory(userEntity.getId(), bookEntity);
    }

    @Override
    @Transactional
    public void borrowBook(BorrowRequest borrowRequest) {
        var book = bookService.getBookEntityByBookCode(borrowRequest.getBookCode());
        var user = userService.getUserEntityByUserName(borrowRequest.getUserName());
        addBookToBorrowHistory(user, book);

        bookInventoryService.decreaseBookQuantity(book);
        bookInventoryService.increaseReadCount(book.getBookInventory().getId());
    }


    public List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long userId) {
        var user = userService.getUserEntity(userId);

        return user.getBookBorrowing().stream()
                .map(bookBorrowHistory -> {
                    var bookEntity = bookBorrowHistory.getBook();
                    return BOOK_LOAN_HISTORY_MAPPER.buildBookBorrowHistoryResponse(bookBorrowHistory, bookEntity);
                })
                .toList();
    }


    private BookBorrowingEntity fetchEntityExist(Long userId, Long bookId) {
        return bookBorrowHistoryRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_BORROW_NOT_FOUND.getMessage())
                );
    }


}
