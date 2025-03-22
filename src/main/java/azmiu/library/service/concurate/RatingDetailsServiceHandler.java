package azmiu.library.service.concurate;

import azmiu.library.dao.entity.RatingDetailsEntity;
import azmiu.library.dao.repository.RatingDetailsRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.dto.RatingCacheDto;
import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.response.RatingDetailsResponse;
import azmiu.library.service.abstraction.CacheService;
import azmiu.library.service.abstraction.RatingDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static azmiu.library.mapper.RatingDetailsMapper.RATING_DETAILS_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingDetailsServiceHandler implements RatingDetailsService {
    private final CacheService cacheService;
    private final RatingDetailsRepository ratingDetailsRepository;

    @Async
    public void insertRatingDetails(RatingDto ratingDto) {
        insertOrUpdateRatingDetails(ratingDto, true);
    }

    @Async
    public void updateRatingDetails(RatingDto ratingDto) {
        insertOrUpdateRatingDetails(ratingDto, false);
    }

    private void insertOrUpdateRatingDetails(RatingDto ratingDto, boolean isInsertOperation) {
        var cacheData = cacheService.get(ratingDto.getBookInventoryId());
        int updatedVoteCount;
        BigDecimal updatedAverageRating;

        if (cacheData != null) {
            updatedVoteCount = isInsertOperation ? cacheData.getVoteCount() + 1 : calculateVoteCount(ratingDto, cacheData);
            updatedAverageRating = calculateAverageRating(cacheData, ratingDto.getScore(), updatedVoteCount);

        } else {
            log.info("Cache miss for bookInventory ID:{}", ratingDto.getBookInventoryId());
            updatedVoteCount = 1;
            updatedAverageRating = BigDecimal.valueOf(ratingDto.getScore());
        }

        cacheService.save(ratingDto.getBookInventoryId(), updatedVoteCount, updatedAverageRating);
        insertOrUpdateRatingDetails(ratingDto.getBookInventoryId(), updatedVoteCount, updatedAverageRating);

    }

    private Integer calculateVoteCount(RatingDto ratingDto, RatingCacheDto ratingCacheDto) {
        if (ratingDto.getStatus().equals(CommonStatus.REMOVED)) {
            return ratingCacheDto.getVoteCount() - 1;
        } else {
            return ratingCacheDto.getVoteCount();
        }
    }

    private void insertOrUpdateRatingDetails(Long bookInventoryId, Integer voteCount, BigDecimal averageRating) {
        var ratingDetails = ratingDetailsRepository.findByBookInventoryId(bookInventoryId)
                .map(existingRatingDetails -> updateExistingRatingDetails(existingRatingDetails, voteCount, averageRating))
                .orElseGet(() -> RATING_DETAILS_MAPPER.buildRatingDetailsEntity(bookInventoryId, voteCount, averageRating));

        ratingDetailsRepository.save(ratingDetails);
    }

    private RatingDetailsEntity updateExistingRatingDetails(RatingDetailsEntity existingRatingDetails, Integer voteCount, BigDecimal averageRating) {
        RATING_DETAILS_MAPPER.updateRatingDetails(existingRatingDetails, voteCount, averageRating);
        return existingRatingDetails;
    }


    private BigDecimal calculateAverageRating(RatingCacheDto ratingCacheDto, Integer newScore, Integer updatedVoteCount) {
        log.info("New score :{}", newScore);
        return ratingCacheDto.getAverageRating()
                .multiply(BigDecimal.valueOf(ratingCacheDto.getVoteCount())).add(BigDecimal.valueOf(newScore))
                .divide(BigDecimal.valueOf(updatedVoteCount), 1, RoundingMode.UP);
    }


    @Override
    public RatingDetailsResponse getRatingDetails(Long bookInventoryId) {
        return RATING_DETAILS_MAPPER.buildRatingDetailsResponse(findByBookInventoryId(bookInventoryId));
    }

    private RatingDetailsEntity findByBookInventoryId(Long bookInventoryId){
        return ratingDetailsRepository.findByBookInventoryId( bookInventoryId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.RATING_DETAILS_NOT_FOUND.getMessage())
                );
    }

}
