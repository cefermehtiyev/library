package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookBorrowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookBorrowHistoryRepository extends JpaRepository<BookBorrowingEntity,Long> {
    Optional<BookBorrowingEntity> findByUserIdAndBookId(Long userId, Long bookId);
    boolean existsByBookId(Long id);

    @Query("SELECT b FROM BookBorrowingEntity b WHERE b.book.id = :bookId AND b.borrowStatus.status = 'PENDING'")
    Optional<BookBorrowingEntity> findByBookIdAndStatusPending(Long bookId);
}
