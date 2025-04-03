package azmiu.library.service.concurate;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.RatingDetailsEntity;
import azmiu.library.dao.repository.RatingDetailsRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.dto.RatingCacheDto;
import azmiu.library.model.dto.RatingDetailsDto;
import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.response.RatingDetailsResponse;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.CacheService;
import azmiu.library.service.abstraction.RatingDetailsService;
import jakarta.servlet.ServletConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

import static azmiu.library.mapper.RatingDetailsMapper.RATING_DETAILS_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingDetailsServiceHandler implements RatingDetailsService {
    private final CacheService cacheService;
    private final RatingDetailsRepository ratingDetailsRepository;
    private final BookInventoryService bookInventoryService;
    private final ServletConfig servletConfig;


    public void initializeRatingDetails(BookInventoryEntity bookInventoryEntity) {
        var ratingDetails = RATING_DETAILS_MAPPER.createDefaultRatingDetails();
        ratingDetails.setBookInventory(bookInventoryEntity);
        ratingDetailsRepository.save(ratingDetails);
    }

    @Async
    @Override
    @Transactional
    public void insertRatingDetails(RatingDto ratingDto) {
        computeRatingDetails(ratingDto,
                (ratingCacheDto, score) -> addScoreToRatingDetails(ratingCacheDto, score, ratingCacheDto.getVoteCount() + 1));
    }

    @Async
    @Override
    @Transactional
    public void updateRatingDetails(RatingDto ratingDto) {
        computeRatingDetails(ratingDto,
                ((ratingCacheDto, score) -> addScoreToRatingDetails(ratingCacheDto, score, ratingCacheDto.getVoteCount())));
    }

    @Async
    @Override
    @Transactional
    public void removeRatingDetails(RatingDto ratingDto) {
        computeRatingDetails(ratingDto,
                ((ratingCacheDto, score) -> subtractScoreFromRatingDetails(ratingCacheDto, score, ratingCacheDto.getVoteCount() - 1)));

    }

    private void computeRatingDetails(RatingDto ratingDto,
                                      BiFunction<RatingCacheDto, Integer, RatingDetailsDto> averageRatingCalculator) {
        var cacheData = cacheService.get(ratingDto.getBookInventoryId());
        RatingDetailsDto ratingDetailsDto;

        if (cacheData != null) {
            ratingDetailsDto = averageRatingCalculator.apply(cacheData, ratingDto.getScore());
        } else {
            ratingDetailsDto = RATING_DETAILS_MAPPER.initializeRatingDetailsDto(ratingDto);
        }
        System.out.println(ratingDetailsDto);

        cacheService.save(ratingDto.getBookInventoryId(), ratingDetailsDto.getVoteCount(), ratingDetailsDto.getAverageRating());
        insertOrUpdateRatingDetails(ratingDto.getBookInventoryId(), ratingDetailsDto.getVoteCount(), ratingDetailsDto.getAverageRating());
    }


    private void insertOrUpdateRatingDetails(Long bookInventoryId, Integer voteCount, BigDecimal averageRating) {
        var ratingDetails = ratingDetailsRepository.findByBookInventoryId(bookInventoryId)
                .map(existingRatingDetails -> updateExistingRatingDetails(existingRatingDetails, voteCount, averageRating))
                .orElseGet(() -> {
                    var bookInventory = bookInventoryService.getBookInventoryEntity(bookInventoryId);
                    return RATING_DETAILS_MAPPER.buildRatingDetailsEntity(bookInventory, voteCount, averageRating);

                });

        ratingDetailsRepository.save(ratingDetails);

    }

    private RatingDetailsEntity updateExistingRatingDetails(RatingDetailsEntity existingRatingDetails, Integer voteCount, BigDecimal averageRating) {
        RATING_DETAILS_MAPPER.updateRatingDetails(existingRatingDetails, voteCount, averageRating);
        return existingRatingDetails;
    }


    private RatingDetailsDto addScoreToRatingDetails(RatingCacheDto ratingCacheDto, Integer newScore, Integer updatedVoteCount) {
        BigDecimal averageRating;
        if (updatedVoteCount == 0) {
            averageRating = BigDecimal.valueOf(0.0);
        } else {
            averageRating = addScoreToAverage(ratingCacheDto, newScore, updatedVoteCount);
        }

        return RATING_DETAILS_MAPPER.buildRatingDetailsDto(updatedVoteCount, averageRating);
    }

    private RatingDetailsDto subtractScoreFromRatingDetails(RatingCacheDto ratingCacheDto, Integer newScore, Integer updatedVoteCount) {
        var averageRating = subtractScoreFromAverage(ratingCacheDto, newScore, updatedVoteCount);
        return RATING_DETAILS_MAPPER.buildRatingDetailsDto(updatedVoteCount, averageRating);
    }

    private BigDecimal addScoreToAverage(RatingCacheDto ratingCacheDto, Integer newScore, Integer updatedVoteCount) {
        return ratingCacheDto.getAverageRating()
                .multiply(BigDecimal.valueOf(ratingCacheDto.getVoteCount()))
                .add(BigDecimal.valueOf(newScore))
                .divide(BigDecimal.valueOf(updatedVoteCount), 1, RoundingMode.UP);
    }

    private BigDecimal subtractScoreFromAverage(RatingCacheDto ratingCacheDto, Integer newScore, Integer updatedVoteCount) {
        return ratingCacheDto.getAverageRating()
                .multiply(BigDecimal.valueOf(ratingCacheDto.getVoteCount()))
                .subtract(BigDecimal.valueOf(newScore))
                .divide(BigDecimal.valueOf(updatedVoteCount), 1, RoundingMode.UP);
    }

    @Override
    public RatingDetailsResponse getRatingDetails(Long bookInventoryId) {
        return RATING_DETAILS_MAPPER.buildRatingDetailsResponse(findByBookInventoryId(bookInventoryId));
    }

    private RatingDetailsEntity findByBookInventoryId(Long bookInventoryId) {
        return ratingDetailsRepository.findByBookInventoryId(bookInventoryId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.RATING_DETAILS_NOT_FOUND.getMessage())
                );
    }

}
