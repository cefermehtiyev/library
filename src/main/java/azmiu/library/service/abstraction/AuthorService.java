package azmiu.library.service.abstraction;

import azmiu.library.criteria.AuthorCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.model.request.AuthorRequest;
import azmiu.library.model.response.AuthorResponse;
import azmiu.library.model.response.PageableResponse;

public interface AuthorService {
    void addAuthor(AuthorRequest authorRequest);

    AuthorResponse getAuthor(Long authorId);

    void updateAuthor(Long authorId, AuthorRequest authorRequest);

    void deleteAuthor(Long authorId);

    void addBookToAuthor(BookEntity bookEntity);

    PageableResponse getAuthorSorted(PageCriteria pageCriteria,AuthorCriteria authorCriteria );

    PageableResponse getBooksByAuthor(Long authorId, PageCriteria pageCriteria);


}
