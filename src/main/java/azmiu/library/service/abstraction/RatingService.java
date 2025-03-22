package azmiu.library.service.abstraction;

import azmiu.library.model.request.RatingRequest;
import azmiu.library.model.response.RatingResponse;

public interface RatingService {
    void insertOrUpdateRating(RatingRequest ratingRequest);

    void removeRating(Long bookInventoryId, Long userId);

    RatingResponse getUserRating(Long bookId, Long userId);
}
