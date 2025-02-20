package az.ingress.dao.repository;

import az.ingress.dao.entity.BorrowStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowStatusRepository extends JpaRepository<BorrowStatusEntity,Long> {

}
