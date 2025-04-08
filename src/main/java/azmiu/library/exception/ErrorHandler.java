package azmiu.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.Optional;

import static azmiu.library.exception.ErrorMessage.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.ALREADY_REPORTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        log.error("Exception: ", ex);
        return new ErrorResponse(UNEXPECTED_ERROR.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse handle(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException: ", ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handle(AlreadyExistsException ex){
        log.error("AlreadyExistsException: ",ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException ex) {
        log.error("NotFoundException: ", ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidFileURLException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(InvalidFileURLException ex){
        log.error("InvalidFileURLException: ",ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ResourceHasRelationsException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handle(ResourceHasRelationsException ex){
        log.error("ResourceHasRelationsException: ",ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: ", ex);
        return new ErrorResponse(
                Optional.ofNullable(ex.getFieldError())
                        .map(FieldError::getDefaultMessage)
                        .orElse("Validation error occurred")
        );
    }

    @ExceptionHandler(BookCurrentlyBorrowedException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handle(BookCurrentlyBorrowedException ex){
        log.error("BookCurrentlyBorrowedException: ",ex);
        return new ErrorResponse(ex.getMessage());
    }



    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handle(AuthException ex) {
        log.error("AuthException: ", ex);
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(new ErrorResponse(ex.getMessage()));
    }



}