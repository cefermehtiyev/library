package az.ingress.dao.repository;

import az.ingress.dao.entity.BookBorrowHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookBorrowHistoryRepository extends JpaRepository<BookBorrowHistoryEntity,Long> {
    Optional<BookBorrowHistoryEntity> findByUserIdAndBookId(Long userId, Long bookiD);
}
