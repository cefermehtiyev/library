package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.entity.RatingEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.request.RatingRequest;
import azmiu.library.model.response.RatingResponse;

public enum RatingMapper {
    RATING_MAPPER;

    public RatingEntity buildRatingEntity(BookInventoryEntity bookInventoryEntity, UserEntity userEntity, RatingRequest ratingRequest, CommonStatusEntity status) {
        return RatingEntity
                .builder()
                .user(userEntity)
                .bookInventory(bookInventoryEntity)
                .commonStatus(status)
                .score(ratingRequest.getScore())
                .build();
    }



    public RatingResponse buildRatingResponse(RatingEntity ratingEntity){
        return RatingResponse
                .builder()
                .id(ratingEntity.getId())
                .userId(ratingEntity.getUser().getId())
                .bookInventoryId(ratingEntity.getBookInventory().getId())
                .score(ratingEntity.getScore())
                .status(ratingEntity.getCommonStatus().getStatus())
                .build();
    }

    public void updateRatingEntity(RatingEntity ratingEntity, RatingRequest ratingRequest) {
        ratingEntity.setScore(ratingRequest.getScore());
    }

    public RatingDto buildRatingDto(RatingEntity ratingEntity, RatingRequest ratingRequest){
        return RatingDto
                .builder()
                .userId(ratingEntity.getUser().getId())
                .bookInventoryId(ratingEntity.getBookInventory().getId())
                .score(ratingRequest.getScore() - ratingEntity.getScore())
                .status(ratingEntity.getCommonStatus().getStatus())
                .createdAt(ratingEntity.getCreatedAt())
                .updatedAt(ratingEntity.getUpdatedAt())
                .build();
    }

    public RatingDto restoreRatingWithUpdate(RatingEntity ratingEntity, RatingRequest ratingRequest){
        return RatingDto
                .builder()
                .userId(ratingEntity.getUser().getId())
                .bookInventoryId(ratingEntity.getBookInventory().getId())
                .score(ratingRequest.getScore() - ratingEntity.getScore())
                .status(ratingEntity.getCommonStatus().getStatus())
                .createdAt(ratingEntity.getCreatedAt())
                .updatedAt(ratingEntity.getUpdatedAt())
                .build();
    }

    public RatingDto buildRatingDtoOnRemoved(RatingEntity ratingEntity){
        return RatingDto
                .builder()
                .userId(ratingEntity.getUser().getId())
                .bookInventoryId(ratingEntity.getBookInventory().getId())
                .score(-ratingEntity.getScore())
                .status(ratingEntity.getCommonStatus().getStatus())
                .createdAt(ratingEntity.getCreatedAt())
                .updatedAt(ratingEntity.getUpdatedAt())
                .build();
    }

    public RatingDto buildRatingDto(RatingEntity ratingEntity){
        return RatingDto
                .builder()
                .userId(ratingEntity.getUser().getId())
                .bookInventoryId(ratingEntity.getBookInventory().getId())
                .score(ratingEntity.getScore())
                .status(ratingEntity.getCommonStatus().getStatus())
                .createdAt(ratingEntity.getCreatedAt())
                .updatedAt(ratingEntity.getUpdatedAt())
                .build();
    }
}
