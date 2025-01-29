package az.ingress.dao.repository;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    Optional<BookEntity> findByBookCode(String bookCode);
    Optional<BookEntity> findByTitle(String title);
}

