package az.ingress.service.abstraction;

import az.ingress.dao.entity.BookEntity;
import az.ingress.model.request.AuthRequest;
import az.ingress.model.request.AuthorRequest;
import az.ingress.model.response.AuthResponse;
import az.ingress.model.response.AuthorResponse;
import az.ingress.model.response.BookResponse;

import java.util.List;

public interface AuthorService {
    void addAuthor(AuthorRequest authorRequest);

    AuthorResponse getAuthor(Long authorId);

    void updateAuthor(Long authorId, AuthorRequest authorRequest);

    void deleteAuthor(Long authorId);

    void addBookToAuthor(BookEntity bookEntity);

    void removeBookFromAuthor(Long bookId, Long authorId);

    List<BookResponse> getBooksByAuthor(Long authorId);


}
