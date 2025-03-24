package azmiu.library.mapper;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.data.domain.Page;

import java.util.Collections;

public enum BookMapper {
    BOOK_MAPPER;

    public BookEntity buildBookEntity(BookRequest bookRequest, CommonStatusEntity commonStatusEntity){
        return BookEntity.builder()
                .title(bookRequest.getTitle())
                .bookCode(bookRequest.getBookCode())
                .author(bookRequest.getAuthor())
                .commonStatus(commonStatusEntity)
                .publisher(bookRequest.getPublisher())
                .language(bookRequest.getLanguage())
                .description(bookRequest.getDescription())
                .pages(bookRequest.getPages())
                .publicationYear(bookRequest.getPublicationYear())
                .build();
    }

    public BookResponse buildBookResponse(BookEntity bookEntity){
        return BookResponse.builder()
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .publisher(bookEntity.getPublisher())
                .publicationYear(bookEntity.getPublicationYear())
                .bookCode(bookEntity.getBookCode())
                .language(bookEntity.getLanguage())
                .status(bookEntity.getCommonStatus().getStatus())
                .inventoryStatus(bookEntity.getBookInventory().getInventoryStatus().getStatus())
                .description(bookEntity.getDescription())
                .pages(bookEntity.getPages())
                .filePath(bookEntity.getBookInventory().getFile().getFilePath())
                .categoryId(bookEntity.getBookInventory().getCategory().getId())
                .createdAt(bookEntity.getCreatedAt())
                .updatedAt(bookEntity.getUpdatedAt())
                .build();
    }
    public PageableResponse pageableBookResponse(Page<BookEntity> bookEntityPage) {
        return PageableResponse.builder()
                .list(Collections.singletonList((bookEntityPage.map(this::buildBookResponse).toList())))
                .currentPageNumber(bookEntityPage.getNumber())
                .totalPages(bookEntityPage.getTotalPages())
                .totalElements(bookEntityPage.getTotalElements())
                .numberOfElements(bookEntityPage.getNumberOfElements())
                .hasNextPage(bookEntityPage.hasNext())
                .build();
    }

    public void updateBookEntity(BookEntity bookEntity,BookRequest bookRequest){
        bookEntity.setTitle(bookRequest.getTitle());
        bookEntity.setAuthor(bookRequest.getAuthor());
        bookEntity.setLanguage(bookRequest.getLanguage());
        bookEntity.setDescription(bookRequest.getDescription());
        bookEntity.setPublisher(bookRequest.getPublisher());
        bookEntity.setPages(bookRequest.getPages());
        bookEntity.setPublicationYear(bookRequest.getPublicationYear());
    }

    public void updateBookStatus(BookEntity book, CommonStatusEntity status){
        book.setCommonStatus(status);
    }


}
