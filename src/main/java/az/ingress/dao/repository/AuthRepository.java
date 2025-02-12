package az.ingress.dao.repository;

import az.ingress.dao.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthorEntity,Long> {
}
