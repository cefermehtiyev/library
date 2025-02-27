package azmiu.library.mapper;

import azmiu.library.dao.entity.AuthorEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.model.request.AuthorRequest;
import azmiu.library.model.response.AuthorResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.data.domain.Page;

import java.util.Collections;


public enum AuthorMapper {
    AUTHOR_MAPPER;

    public AuthorEntity buildAuthorEntity(AuthorRequest authorRequest, CommonStatusEntity commonStatus ) {
        return AuthorEntity.builder()
                .name(authorRequest.getName())
                .commonStatusEntity(commonStatus)
                .biography(authorRequest.getBiography())
                .dateOfBirth(authorRequest.getDateOfBirth())
                .build();
    }

    public AuthorResponse buildAuthorResponse(AuthorEntity authorEntity) {
        return AuthorResponse.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .status(authorEntity.getCommonStatusEntity().getStatus())
                .biography(authorEntity.getBiography())
                .dateOfBirth(authorEntity.getDateOfBirth())
                .build();
    }

    public void updateAuthor(AuthorEntity authorEntity, AuthorRequest authorRequest) {
        authorEntity.setName(authorRequest.getName());
        authorEntity.setBiography(authorRequest.getBiography());
        authorEntity.setDateOfBirth(authorRequest.getDateOfBirth());
    }

    public PageableResponse pageableAuthorResponse(Page<AuthorEntity> userEntityPage){
        return PageableResponse.builder()
                .list(Collections.singletonList(userEntityPage.map(this::buildAuthorResponse).toList()))
                .currentPageNumber(userEntityPage.getNumber())
                .totalPages(userEntityPage.getTotalPages())
                .totalElements(userEntityPage.getTotalElements())
                .numberOfElements(userEntityPage.getNumberOfElements())
                .hasNextPage(userEntityPage.hasNext())
                .build();
    }
}
