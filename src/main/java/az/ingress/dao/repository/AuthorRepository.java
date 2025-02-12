package az.ingress.dao.repository;

import az.ingress.dao.entity.AuthorEntity;
import az.ingress.dao.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity ,Long> {
    Optional<AuthorEntity> findByName(String name);


}
