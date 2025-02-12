package az.ingress.mapper;

import az.ingress.dao.entity.AuthorEntity;
import az.ingress.model.enums.AuthorStatus;
import az.ingress.model.request.AuthorRequest;
import az.ingress.model.response.AuthorResponse;

import static az.ingress.model.enums.AuthorStatus.ACTIVE;

public enum AuthorMapper {
    AUTHOR_MAPPER;

    public AuthorEntity buildAuthorEntity(AuthorRequest authorRequest) {
        return AuthorEntity.builder()
                .name(authorRequest.getName())
                .biography(authorRequest.getBiography())
                .authorStatus(ACTIVE)
                .dateOfBirth(authorRequest.getDateOfBirth())
                .build();
    }

    public AuthorResponse buildAuthorResponse(AuthorEntity authorEntity) {
        return AuthorResponse.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .biography(authorEntity.getBiography())
                .authorStatus(authorEntity.getAuthorStatus())
                .dateOfBirth(authorEntity.getDateOfBirth())
                .build();
    }

    public void updateAuthor(AuthorEntity authorEntity, AuthorRequest authorRequest) {
        authorEntity.setName(authorRequest.getName());
        authorEntity.setBiography(authorRequest.getBiography());
        authorEntity.setDateOfBirth(authorRequest.getDateOfBirth());
    }
}
