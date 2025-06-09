package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookBorrowingEntity;
import azmiu.library.dao.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookBorrowHistoryRepository extends JpaRepository<BookBorrowingEntity,Long> {
    Optional<BookBorrowingEntity> findByUserIdAndBookId(Long userId, Long bookId);
    boolean existsByBookId(Long id);

    @Query("SELECT b FROM BookBorrowingEntity b WHERE b.book.id = :bookId AND b.borrowStatus.status = 'PENDING'")
    Optional<BookBorrowingEntity> findByBookIdAndStatusPending(Long bookId);



    @Query("""
    SELECT DISTINCT b FROM BookBorrowingEntity br
    JOIN br.book b
    JOIN FETCH b.bookInventory
    JOIN FETCH b.bookInventory.ratingDetails
    JOIN FETCH b.bookInventory.inventoryStatus
    JOIN FETCH b.bookInventory.file
    JOIN FETCH b.bookInventory.image
    JOIN FETCH b.commonStatus
    WHERE br.user.fin = :fin
""")
    Page<BookEntity> findBooksByUserFin(@Param("fin") String fin, Pageable pageable);


}
