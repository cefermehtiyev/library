package azmiu.library.mapper;

import azmiu.library.dao.entity.RatingCacheOutboxEntity;
import azmiu.library.model.dto.RatingCacheDto;

public enum RatingCacheOutboxMapper {
    RATING_CACHE_OUTBOX_MAPPER;

    public RatingCacheOutboxEntity buildCacheOutBoxEntity(Long bookInventoryId, RatingCacheDto cacheDto){
        return RatingCacheOutboxEntity.builder()
                .bookInventoryId(bookInventoryId)
                .voteCount(cacheDto.getVoteCount())
                .averageRating(cacheDto.getAverageRating())
                .processed(false)
                .build();
    }
}
