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
    EMPLOYEE_NOT_FOUND("User not found"),
    AUTHOR_NOT_FOUND("Author not found"),
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
    ACCESS_TOKEN_NOT_FOUND("Access token not found"),
    RATING_NOT_FOUND("Rating not found"),
    RATING_DETAILS_NOT_FOUND("Rating details not found"),
    FILE_NOT_FOUND("File not found"),
    SAVED_BOOK_NOT_FOUND("Saved book not found"),
    STUDENT_NOT_FOUND("Student not found"),
    IMAGE_NOT_FOUND("Image not Found"),
    COMMON_STATUS_NOT_FOUND("Common status not found"),
    INVENTORY_STATUS_NOT_FOUND("Inventory status not found"),
    BORROW_STATUS_NOT_FOUND("Borrow status not found"),
    USER_ROLE_NOT_FOUND("User role not Found"),
    BOOK_BORROW_NOT_FOUND("Book borrow not found"),
    BOOK_INVENTORY_NOT_FOUND("Book not Found in inventory"),

    BOOK_CURRENTLY_BORROWED_EXCEPTION("Book currently borrowed"),
    CATEGORY_HAS_RELATED_BOOKS("Category cannot be deleted because it has related books"),
    CATEGORY_ALREADY_EXISTS("Category already exists"),
    BOOK_ALREADY_EXISTS("Book already exists"),
    BOOK_CODE_ALREADY_EXISTS("Book code already exists"),
    SAVED_BOOK_ALREADY_EXISTS("Saved book already exists"),

    FILE_STORAGE_FAILURE("File storage operation failed"),
    OUT_OF_STOCK("The book is out of stock"),

    BOOK_DATA_MISMATCH_EXCEPTION("Book data mismatch"),
    INVALID_FILE_URL("File url is invalid"),
    INVALID_BOOK_UPDATE_EXCEPTION("Invalid book update Exception");




    private final String message;
}