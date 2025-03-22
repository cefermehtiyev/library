package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.RatingEntity;
import azmiu.library.dao.repository.RatingRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.mapper.RatingDetailsMapper;
import azmiu.library.mapper.RatingMapper;
import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.request.RatingRequest;
import azmiu.library.model.response.RatingResponse;
import azmiu.library.service.abstraction.BookInventoryService;
import azmiu.library.service.abstraction.BookService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.abstraction.RatingDetailsService;
import azmiu.library.service.abstraction.RatingService;
import azmiu.library.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static azmiu.library.mapper.RatingDetailsMapper.RATING_DETAILS_MAPPER;
import static azmiu.library.mapper.RatingMapper.RATING_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceHandler implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingDetailsService ratingDetailsService;
    private final BookInventoryService bookInventoryService;
    private final UserService userService;
    private final CommonStatusConfig commonStatusConfig;
    private final CommonStatusService commonStatusService;

    @Override
    @Transactional
    public void insertOrUpdateRating(RatingRequest ratingRequest) {
        var bookEntity = bookInventoryService.getBookInventoryEntity(ratingRequest.getBookInventoryId());
        var userEntity = userService.getUserEntity(ratingRequest.getUserId());
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        var ratingEntityOptional = ratingRepository.findByBookInventoryIdAndUserId(ratingRequest.getBookInventoryId(), ratingRequest.getUserId());
        var ratingEntity = ratingEntityOptional
                .map(existingRating -> {
                    ratingDetailsService.updateRatingDetails(RATING_MAPPER.buildRatingDto(existingRating, ratingRequest));
                    updateExistingRating(existingRating, ratingRequest);
                    existingRating.setCommonStatus(status);
                    return existingRating;
                })
                .orElseGet(() -> {
                    var entity = RATING_MAPPER.buildRatingEntity(bookEntity, userEntity, ratingRequest, status);
                    ratingDetailsService.insertRatingDetails(RATING_MAPPER.buildRatingDto(entity));
                    return entity;
                });
        ratingRepository.save(ratingEntity);

    }

    private void updateExistingRating(RatingEntity existingRating, RatingRequest ratingRequest) {
        log.info("Rating updated");
        RATING_MAPPER.updateRatingEntity(existingRating, ratingRequest);
    }

    @Override
    @Transactional
    public void removeRating(Long bookInventoryId, Long userId) {
        var rating = findByBookInventoryIdAndUserId(bookInventoryId, userId);
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getRemoved());
        rating.setCommonStatus(status);
        ratingDetailsService.updateRatingDetails(RATING_MAPPER.buildRatingDto(rating));
        ratingRepository.save(rating);
    }

    @Override
    public RatingResponse getRating(Long bookInventoryId, Long userId) {
        return RATING_MAPPER.buildRatingResponse(findByBookInventoryIdAndUserId(bookInventoryId,userId));
    }

    private RatingEntity findByBookInventoryIdAndUserId(Long bookInventoryId, Long userId) {
        return ratingRepository.findByBookInventoryIdAndUserId(bookInventoryId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.RATING_NOT_FOUND.getMessage()));
    }
}
