package azmiu.library.dao.repository;

import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.model.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonStatusRepository extends JpaRepository<CommonStatusEntity,Long> {
    Optional<CommonStatusEntity> findByStatus(CommonStatus status);

}
