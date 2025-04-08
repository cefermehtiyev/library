package azmiu.library.exception;

public class BookCurrentlyBorrowedException extends RuntimeException {
    public BookCurrentlyBorrowedException(String message) {
        super(message);
    }
}
