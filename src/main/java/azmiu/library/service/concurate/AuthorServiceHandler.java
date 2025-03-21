package azmiu.library.service.concurate;

import azmiu.library.annotation.Log;
import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.criteria.AuthorCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.AuthorEntity;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.repository.AuthorRepository;
import azmiu.library.dao.repository.CommonStatusRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.request.AuthorRequest;

import azmiu.library.model.response.AuthorResponse;

import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.AuthorService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.specification.AuthorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static azmiu.library.mapper.AuthorMapper.AUTHOR_MAPPER;
import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;

@Log
@RequiredArgsConstructor
@Service
public class AuthorServiceHandler implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;


    @Override
    @Transactional
    public void addAuthor(AuthorRequest authorRequest) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        authorRepository.save(AUTHOR_MAPPER.buildAuthorEntity(authorRequest, status));
    }

    @Override
    public AuthorResponse getAuthor(Long authorId) {
        return AUTHOR_MAPPER.buildAuthorResponse(fetchEntityExist(authorId));
    }

    @Override
    public void updateAuthor(Long authorId, AuthorRequest authorRequest) {
        var authorEntity = fetchEntityExist(authorId);

        AUTHOR_MAPPER.updateAuthor(authorEntity, authorRequest);
        authorRepository.save(authorEntity);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        var author = fetchEntityExist(authorId);
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getRemoved());
        author.setCommonStatus(status);
        authorRepository.save(author);
    }

    @Override
    public void addBookToAuthor(BookEntity bookEntity) {
        var authorEntity = fetchEntityExist(bookEntity.getAuthor());
        authorEntity.getBooks().add(bookEntity);

    }


    @Override
    public PageableResponse getAuthorSorted(PageCriteria pageCriteria, AuthorCriteria authorCriteria) {
        var page = authorRepository.findAll(
                new AuthorSpecification(authorCriteria), PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())

        );
        return AUTHOR_MAPPER.pageableAuthorResponse(page);

    }


    @Override
    public PageableResponse getBooksByAuthor(Long authorId, PageCriteria pageCriteria) {
        var page = authorRepository.findBooksByAuthor(authorId,
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );
        return BOOK_MAPPER.pageableBookResponse(page);
    }

    private AuthorEntity fetchEntityExist(String name) {
        return authorRepository.findByName(name).orElseThrow(
                () -> new NotFoundException(ErrorMessage.AUTHOR_NOT_FOUND.getMessage())
        );
    }

    private AuthorEntity fetchEntityExist(Long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.AUTHOR_NOT_FOUND.getMessage())
        );
    }
}
