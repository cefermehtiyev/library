package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    @Query("SELECT b FROM BookEntity b WHERE b.bookCode = :bookCode AND b.commonStatus.status = 'ACTIVE'")
    Optional<BookEntity> findActiveBookByBookCode(String bookCode);

    @Query("SELECT b FROM BookEntity b WHERE b.bookCode = :bookCode AND b.commonStatus.status = 'INACTIVE'")
    Optional<BookEntity> findInActiveBookByBookCode(String bookCode);

    Optional<BookEntity> findByBookCode(String bookCode);

    boolean existsByBookCode(String bookCode);

    @Query("SELECT b FROM BookEntity b " +
            "JOIN b.bookInventory bi " +
            "WHERE b.commonStatus.status <> 'DELETED' " +
            "AND b.id = (SELECT MIN(b2.id) FROM BookEntity b2 WHERE b2.title = b.title) " +
            "ORDER BY " +
            "CASE WHEN :order = 'desc' THEN b.pages END DESC, " +
            "CASE WHEN :order = 'asc' THEN b.pages END ASC")
    Page<BookEntity> findDistinctBooks(String order, Pageable pageable);


}

