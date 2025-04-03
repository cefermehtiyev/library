package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.model.dto.RatingDto;
import azmiu.library.model.response.RatingDetailsResponse;

public interface RatingDetailsService {
    void initializeRatingDetails(BookInventoryEntity bookInventoryEntity);

    void insertRatingDetails(RatingDto ratingDto);

    void updateRatingDetails(RatingDto ratingDto);

    RatingDetailsResponse getRatingDetails(Long bookInventoryId);

    void removeRatingDetails(RatingDto ratingDto);


}
