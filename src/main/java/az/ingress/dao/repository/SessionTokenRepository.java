package az.ingress.dao.repository;


import az.ingress.dao.entity.SessionTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionTokenRepository extends JpaRepository<SessionTokenEntity, Long> {
    Optional<SessionTokenEntity> findByUserId(String userId);
}
