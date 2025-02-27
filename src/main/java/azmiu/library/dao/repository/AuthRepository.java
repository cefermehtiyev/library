package azmiu.library.dao.repository;

import azmiu.library.dao.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthorEntity,Long> {
}
