package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookInventoryRepository extends JpaRepository<BookInventoryEntity, Long> {
    Optional<BookInventoryEntity> findByTitleAndPublicationYear(String title, Integer publicationYear);

    @Query("SELECT b FROM BookEntity b " +
            "JOIN b.bookInventoryEntity bi " +
            "WHERE b.id = (SELECT MIN(b2.id) FROM BookEntity b2 WHERE b2.title = b.title)" +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'createAt' AND :order = 'asc' THEN bi.createdAt END ASC," +
            "CASE WHEN :sortBy = 'createdAt' AND :order = 'desc'THEN bi.createdAt END DESC," +
            "CASE WHEN :sortBy = 'readCount' AND :order = 'asc' THEN bi.readCount END ASC ," +
            "CASE WHEN :sortBy = 'readCount' AND :order = 'desc' THEN bi.readCount END DESC "
    )
    Page<BookEntity> findDistinctBooks(@Param("sortBy") String sortBy, @Param("order") String order, Pageable pageable);


}

