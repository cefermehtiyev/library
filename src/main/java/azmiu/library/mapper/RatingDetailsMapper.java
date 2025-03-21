package azmiu.library.mapper;

import azmiu.library.dao.entity.RatingDetailsEntity;
import azmiu.library.dao.entity.RatingEntity;
import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.response.RatingDetailsResponse;

import java.math.BigDecimal;

public enum RatingDetailsMapper {
    RATING_DETAILS_MAPPER;

    public RatingDetailsEntity buildRatingDetailsEntity(Long bookInventoryId, Integer voteCount, BigDecimal averageRating){
        return RatingDetailsEntity.builder()
                .bookInventoryId(bookInventoryId)
                .voteCount(voteCount)
                .averageRating(averageRating)
                .build();
    }

    public void updateRatingDetails(RatingDetailsEntity ratingDetailsEntity, Integer voteCount, BigDecimal averageRating ){
        ratingDetailsEntity.setVoteCount(voteCount);
        ratingDetailsEntity.setAverageRating(averageRating);
    }

    public RatingDetailsResponse buildRatingDetailsResponse(RatingDetailsEntity ratingDetailsEntity){
        return RatingDetailsResponse.builder()
                .bookInventoryId(ratingDetailsEntity.getBookInventoryId())
                .voteCount(ratingDetailsEntity.getVoteCount())
                .averageRating(ratingDetailsEntity.getAverageRating())
                .build();
    }


}
