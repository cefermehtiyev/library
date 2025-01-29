package az.ingress.dao.repository;

import az.ingress.dao.entity.BookBorrowHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookBorrowHistoryRepository extends JpaRepository<BookBorrowHistoryEntity,Long> {
}
