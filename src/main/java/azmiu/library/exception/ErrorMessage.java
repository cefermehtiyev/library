package azmiu.library.exception;

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
    BOOK_CURRENTLY_BORROWED_EXCEPTION("Book currently borrowed"),
    EMPLOYEE_NOT_FOUND("User not found"),
    AUTHOR_NOT_FOUND("Author not found"),
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
    ACCESS_TOKEN_NOT_FOUND("Access token not found"),
    RATING_NOT_FOUND("Rating not found"),
    RATING_DETAILS_NOT_FOUND("Rating details not found"),
    FILE_NOT_FOUND("File not found"),
    SAVED_BOOK_NOT_FOUND("Saved book not found"),
    CATEGORY_HAS_RELATED_BOOKS("Category cannot be deleted because it has related books"),
    CATEGORY_ALREADY_EXISTS("Category Already Exists"),
    BOOK_ALREADY_EXISTS("Book Already Exists"),
    INVALID_FILE_URL("File url is invalid"),
    FILE_STORAGE_FAILURE("File storage operation failed"),
    STUDENT_NOT_FOUND("Student not found"),
    OUT_OF_STOCK("The book is out of stock"),
    BOOK_BORROW_NOT_FOUND("Book borrow not found"),
    BOOK_INVENTORY_NOT_FOUND("Book not Found in inventory"),
    IMAGE_NOT_FOUND("Image not Found"),
    COMMON_STATUS_NOT_FOUND("Common status not found"),
    INVENTORY_STATUS_NOT_FOUND("Inventory status not found"),
    BORROW_STATUS_NOT_FOUND("Borrow status not found"),
    USER_ROLE_NOT_FUND("User role not Found");



    private final String message;
}