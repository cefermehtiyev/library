package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BorrowStatusEntity;
import azmiu.library.model.enums.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowStatusRepository extends JpaRepository<BorrowStatusEntity,Long> {
    Optional<BorrowStatusEntity> findByStatus(BorrowStatus status);

}
