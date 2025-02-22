package az.ingress.service.abstraction;

import az.ingress.criteria.AuthorCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.model.request.AuthorRequest;
import az.ingress.model.response.AuthorResponse;
import az.ingress.model.response.PageableResponse;

public interface AuthorService {
    void addAuthor(AuthorRequest authorRequest);

    AuthorResponse getAuthor(Long authorId);

    void updateAuthor(Long authorId, AuthorRequest authorRequest);

    void deleteAuthor(Long authorId);

    void addBookToAuthor(BookEntity bookEntity);

    PageableResponse getAuthorSorted(PageCriteria pageCriteria,AuthorCriteria authorCriteria );

    PageableResponse getBooksByAuthor(Long authorId, PageCriteria pageCriteria);


}
