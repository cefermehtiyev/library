package az.ingress.dao.repository;

import az.ingress.criteria.PageCriteria;
import az.ingress.dao.entity.AuthorEntity;
import az.ingress.dao.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity ,Long>, JpaSpecificationExecutor<AuthorEntity> {
    Optional<AuthorEntity> findByName(String name);

    @Query("SELECT b FROM BookEntity b" +
            " JOIN b.authorEntities a WHERE a.id = :authorId")
    Page<BookEntity> findBooksByAuthor(Long authorId, Pageable pageable);



}
