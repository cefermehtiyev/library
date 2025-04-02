package azmiu.library.mapper;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.RatingDetailsEntity;
import azmiu.library.model.response.RatingDetailsResponse;

import java.math.BigDecimal;

public enum RatingDetailsMapper {
    RATING_DETAILS_MAPPER;

    public RatingDetailsEntity createDefaultRatingDetails(){
        return RatingDetailsEntity
                .builder()
                .voteCount(0)
                .averageRating(BigDecimal.valueOf(0.0))
                .build();
    }

    public RatingDetailsEntity buildRatingDetailsEntity(BookInventoryEntity bookInventory ,Integer voteCount, BigDecimal averageRating){
        return RatingDetailsEntity.builder().bookInventory(bookInventory)
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
                .bookInventoryId(ratingDetailsEntity.getId())
                .voteCount(ratingDetailsEntity.getVoteCount())
                .averageRating(ratingDetailsEntity.getAverageRating())
                .build();
    }


}
