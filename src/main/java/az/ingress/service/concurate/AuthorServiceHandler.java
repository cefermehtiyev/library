package az.ingress.service.concurate;

import az.ingress.dao.entity.AuthorEntity;
import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.repository.AuthorRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.request.AuthorRequest;

import az.ingress.model.response.AuthorResponse;
import az.ingress.model.response.BookResponse;

import az.ingress.service.abstraction.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.mapper.AuthorMapper.AUTHOR_MAPPER;
import static az.ingress.model.enums.AuthorStatus.REMOVED;

@RequiredArgsConstructor
@Service
public class AuthorServiceHandler implements AuthorService {
    private final AuthorRepository authorRepository;


    @Override
    public void addAuthor(AuthorRequest authorRequest) {
        authorRepository.save(AUTHOR_MAPPER.buildAuthorEntity(authorRequest));
    }

    @Override
    public AuthorResponse getAuthor(Long authorId) {
        return AUTHOR_MAPPER.buildAuthorResponse(fetchEntityExist(authorId));
    }

    @Override
    public void updateAuthor(Long authorId, AuthorRequest authorRequest) {
        var authorEntity = fetchEntityExist(authorId);

        AUTHOR_MAPPER.updateAuthor(authorEntity,authorRequest);
        authorRepository.save(authorEntity);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        var author = fetchEntityExist(authorId);
        author.setAuthorStatus(REMOVED);
    }

    @Override
    public void addBookToAuthor(BookEntity bookEntity) {
        var authorEntity = fetchEntityExist(bookEntity.getAuthor());
        authorEntity.getBookEntities().add(bookEntity);

    }

    @Override
    public void removeBookFromAuthor(Long bookId, Long authorId) {

    }

    @Override
    public List<BookResponse> getBooksByAuthor(Long authorId) {
        return List.of();
    }

    private AuthorEntity fetchEntityExist(String name){
        return authorRepository.findByName(name).orElseThrow(
                () -> new NotFoundException(ErrorMessage.AUTHOR_NOT_FOUND.getMessage())
        );
    }
    private AuthorEntity fetchEntityExist(Long id){
        return authorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.AUTHOR_NOT_FOUND.getMessage())
        );
    }
}
