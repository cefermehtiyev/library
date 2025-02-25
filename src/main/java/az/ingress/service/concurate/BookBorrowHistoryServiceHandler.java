package az.ingress.service.concurate;

import az.ingress.configuration.BorrowStatusConfig;
import az.ingress.dao.entity.BookBorrowHistoryEntity;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.BookBorrowHistoryRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.request.BorrowRequest;
import az.ingress.model.response.BookBorrowHistoryResponse;
import az.ingress.service.abstraction.BookBorrowHistoryService;
import az.ingress.service.abstraction.BookInventoryService;
import az.ingress.service.abstraction.BookService;
import az.ingress.service.abstraction.BorrowStatusService;
import az.ingress.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

import static az.ingress.mapper.BookBorrowHistoryMapper.BOOK_LOAN_HISTORY_MAPPER;

@Service
@RequiredArgsConstructor
public class BookBorrowHistoryServiceHandler implements BookBorrowHistoryService {
    private final BookBorrowHistoryRepository bookBorrowHistoryRepository;
    private final UserService userService;
    private final BookInventoryService bookInventoryService;
    private final BookService bookService;
    private final BorrowStatusService borrowStatusService;
    private final BorrowStatusConfig borrowStatusConfig;


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

        return user.getBookBorrowHistoryEntity().stream()
                .map(bookBorrowHistory -> {
                    var bookEntity = bookBorrowHistory.getBook();
                    return BOOK_LOAN_HISTORY_MAPPER.buildBookBorrowHistoryResponse(bookBorrowHistory, bookEntity);
                })
                .toList();
    }


    private BookBorrowHistoryEntity fetchEntityExist(Long userId, Long bookId) {
        return bookBorrowHistoryRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.BOOK_BORROW_NOT_FOUND.getMessage())
                );
    }


}
