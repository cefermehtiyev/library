package az.ingress.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    USER_UNAUTHORIZED("User unauthorized"),
    REFRESH_TOKEN_COUNT_EXPIRED("Refresh token count expired"),
    TOKEN_EXPIRED("Token expired"),
    CATEGORY_NOT_FOUND("Category not found"),
    USER_NOT_FOUND("User not found"),
    BOOK_NOT_FOUND("Book not found"),
    AUTHOR_NOT_FOUND("Author not found"),
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
    ACCESS_TOKEN_NOT_FOUND("Access token not found"),
    FILE_NOT_FOUND("File not found"),
    INVALID_FILE_URL("File url is invalid"),
    FILE_STORAGE_FAILURE("File storage operation failed"),
    STUDENT_NOT_FOUND("Student not found"),
    OUT_OF_STOCK("The book is out of stock"),
    BOOK_BORROW_NOT_FOUND("Book borrow not found");

    private final String message;
}