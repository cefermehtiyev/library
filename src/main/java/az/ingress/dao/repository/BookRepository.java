package az.ingress.dao.repository;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    Optional<BookEntity> findByBookCode(String bookCode);

    @Query(value = "SELECT b.* FROM books b WHERE b.id = (SELECT MIN(id) FROM books WHERE title = b.title)", nativeQuery = true)
    List<BookEntity> findDistinctByTitle();
}

