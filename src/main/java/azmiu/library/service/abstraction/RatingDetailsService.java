package azmiu.library.service.abstraction;

import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.response.RatingDetailsResponse;

public interface RatingDetailsService {
    void updateRatingDetails(RatingDto ratingDto);

    void insertRatingDetails(RatingDto ratingDto);

    RatingDetailsResponse getRatingDetails(Long bookInventoryId);

}
