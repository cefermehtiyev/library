package az.ingress.dao.repository;

import az.ingress.dao.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    Optional<BookEntity> findByBookCode(String bookCode);

    @Query(value = "SELECT b.* FROM books b " +
            "LEFT JOIN book_inventory bi ON bi.id = b.inventory_id " +
            "WHERE b.id = (SELECT MIN(b2.id) FROM books b2 WHERE b2.title = b.title) " +
            "ORDER BY bi.read_count DESC ",
            nativeQuery = true)
    Page<BookEntity> findDistinctBooksByReadCount(Pageable pageable);



}

