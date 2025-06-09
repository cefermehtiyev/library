package azmiu.library.service.abstraction;

import azmiu.library.model.dto.RatingCacheDto;

import java.math.BigDecimal;

public interface CacheService {

    void save(Long bookInventoryId, Integer voteCount, BigDecimal averageRating);

    RatingCacheDto get(Long productId);
}
