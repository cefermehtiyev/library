package az.ingress.dao.repository;

import az.ingress.dao.entity.CommonStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonStatusRepository extends JpaRepository<CommonStatusEntity,Long> {
}
