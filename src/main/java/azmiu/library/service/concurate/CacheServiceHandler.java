package azmiu.library.service.concurate;

import azmiu.library.dao.entity.RatingCacheOutboxEntity;
import azmiu.library.dao.repository.RatingCacheOutboxRepository;
import azmiu.library.dao.repository.RatingDetailsRepository;
import azmiu.library.mapper.RatingCacheOutboxMapper;
import azmiu.library.model.dto.RatingCacheDto;
import azmiu.library.service.abstraction.CacheService;
import azmiu.library.util.CacheProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import static azmiu.library.mapper.CacheDtoMapper.CACHE_DTO_MAPPER;
import static azmiu.library.mapper.RatingCacheOutboxMapper.RATING_CACHE_OUTBOX_MAPPER;
import static azmiu.library.model.constants.CacheConstants.BOOK_CACHE_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceHandler implements CacheService {
    private final CacheProvider cacheProvider;
    private final RatingDetailsRepository ratingDetailsRepository;
    private final RatingCacheOutboxRepository ratingCacheOutboxRepository;

    @Override
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public void save(Long bookInventoryId, Integer voteCount, BigDecimal averageRating) {
        var cacheKey = BOOK_CACHE_KEY + bookInventoryId;
        var cacheDto = CACHE_DTO_MAPPER.buildCacheDto(voteCount, averageRating);
        try {
            cacheProvider.saveToCache(cacheKey, cacheDto, 1L, ChronoUnit.DAYS);
            log.info("Data save to Cache");

        } catch (Exception exception) {
            log.info("Data save to Cache Out Box");
            var outBox = RATING_CACHE_OUTBOX_MAPPER.buildCacheOutBoxEntity(bookInventoryId,cacheDto);
            ratingCacheOutboxRepository.save(outBox);
        }
    }

    @Override
    public RatingCacheDto get(Long bookInventoryId) {
        var cacheKey = BOOK_CACHE_KEY + bookInventoryId;
        var cacheData = cacheProvider.getBucket(cacheKey);
        if (cacheData != null) {
            return (RatingCacheDto) cacheData;
        }
        return ratingDetailsRepository.findByBookInventoryId(bookInventoryId)
                .map(existingRatingDetails -> CACHE_DTO_MAPPER.buildCacheDto(existingRatingDetails.getVoteCount(), existingRatingDetails.getAverageRating()))
                .orElseGet(() -> null);
    }
}
