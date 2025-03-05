package azmiu.library.service.concurate;

import azmiu.library.configuration.BorrowStatusConfig;
import azmiu.library.dao.entity.BookBorrowingEntity;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.BookBorrowHistoryRepository;
import azmiu.library.dao.repository.UserRepository;
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

import jakarta.transaction.Transactional;
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
    private final UserRepository userRepository;


    @Override
    public  void addBookToBorrowHistory(UserEntity userEntity, BookEntity bookEntity) {
        var status = borrowStatusService.getBorrowStatus(borrowStatusConfig.getPending());
        bookBorrowHistoryRepository.save(BOOK_LOAN_HISTORY_MAPPER.buildBookBorrowHistoryEntity(userEntity, bookEntity,status));
    }



    @Override
    public void returnBookHistory(Long userId, Long bookId) {
        var bookBorrowHistory = fetchEntityExist(userId, bookId);
        var status = borrowStatusService.getBorrowStatus(borrowStatusConfig.getReturned());
        BOOK_LOAN_HISTORY_MAPPER.updateReturnBookBorrowHistory(bookBorrowHistory,status);
        bookBorrowHistoryRepository.save(bookBorrowHistory);
    }

    @Override
    public void processBookReturn(BorrowRequest borrowRequest) {
        var book = bookService.getBookEntityByBookCode(borrowRequest.getBookCode());
        var user = userService.getUserEntityByFin(borrowRequest.getFin());
        System.out.println(bookBorrowHistoryRepository.findByUserIdAndBookId(user.getId(),book.getId()).get());
        returnBookHistory(user.getId(), book.getId());
    }

    @Override
    @Transactional
    public void borrowBook(BorrowRequest borrowRequest) {
        var book = bookService.getBookEntityByBookCode(borrowRequest.getBookCode());
        var user = userService.getUserEntityByFin(borrowRequest.getFin());
        addBookToBorrowHistory(user, book);

        bookInventoryService.decreaseBookQuantity(book);
        bookInventoryService.increaseReadCount(book.getBookInventoryEntity().getId());
    }


    public List<BookBorrowHistoryResponse> getBorrowedBooksByStudent(Long userId) {
        var user = userService.getUserEntity(userId);

        return user.getBookBorrowingEntity().stream()
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
