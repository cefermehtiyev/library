package azmiu.library.dao.repository;

import azmiu.library.dao.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity,Long> {
    Optional<RatingEntity> findByBookInventoryIdAndUserId( Long bookId, Long userId);
}
