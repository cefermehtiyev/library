package azmiu.library.dao.repository;

import azmiu.library.dao.entity.RatingCacheOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingCacheOutboxRepository extends JpaRepository<RatingCacheOutboxEntity, Long> {
    List<RatingCacheOutboxEntity> findByProcessedFalse();
}
