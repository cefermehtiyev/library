package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookBorrowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookBorrowHistoryRepository extends JpaRepository<BookBorrowingEntity,Long> {
    Optional<BookBorrowingEntity> findByUserIdAndBookId(Long userId, Long bookId);
}
