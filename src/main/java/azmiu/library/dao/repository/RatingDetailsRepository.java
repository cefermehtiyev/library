package azmiu.library.dao.repository;

import azmiu.library.dao.entity.RatingDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingDetailsRepository extends JpaRepository<RatingDetailsEntity, Long> {
    Optional<RatingDetailsEntity> findByBookInventoryId(Long id);
}
