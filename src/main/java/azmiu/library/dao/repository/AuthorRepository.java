package azmiu.library.dao.repository;

import azmiu.library.dao.entity.AuthorEntity;
import azmiu.library.dao.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity ,Long>, JpaSpecificationExecutor<AuthorEntity> {
    Optional<AuthorEntity> findByName(String name);

    @Query("SELECT b FROM BookEntity b" +
            " JOIN b.authors a WHERE a.id = :authorId")
    Page<BookEntity> findBooksByAuthor(Long authorId, Pageable pageable);



}
