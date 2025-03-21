package azmiu.library.mapper;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.entity.RatingEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.request.RatingRequest;

public enum RatingMapper {
    RATING_MAPPER;

    public RatingEntity buildRatingEntity(BookInventoryEntity bookInventoryEntity, UserEntity userEntity, RatingRequest ratingRequest, CommonStatusEntity status) {
        return RatingEntity.builder()
                .user(userEntity)
                .bookInventory(bookInventoryEntity)
                .commonStatus(status)
                .score(ratingRequest.getScore())
                .build();
    }

    public void updateRatingEntity(RatingEntity ratingEntity, RatingRequest ratingRequest) {
        ratingEntity.setScore(ratingRequest.getScore());
    }

    public RatingDto buildRatingDto(RatingEntity ratingEntity, RatingRequest ratingRequest){
        return RatingDto.builder()
                .userId(ratingEntity.getUser().getId())
                .bookInventoryId(ratingEntity.getBookInventory().getId())
                .score(ratingRequest.getScore() - ratingEntity.getScore())
                .status(ratingEntity.getCommonStatus().getStatus())
                .createdAt(ratingEntity.getCreatedAt())
                .updatedAt(ratingEntity.getUpdatedAt())
                .build();
    }

    public RatingDto buildRatingDto(RatingEntity ratingEntity){
        return RatingDto.builder()
                .userId(ratingEntity.getUser().getId())
                .bookInventoryId(ratingEntity.getBookInventory().getId())
                .score(ratingEntity.getScore())
                .status(ratingEntity.getCommonStatus().getStatus())
                .createdAt(ratingEntity.getCreatedAt())
                .updatedAt(ratingEntity.getUpdatedAt())
                .build();
    }
}
