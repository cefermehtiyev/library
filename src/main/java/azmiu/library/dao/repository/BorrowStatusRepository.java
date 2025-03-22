package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BorrowStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowStatusRepository extends JpaRepository<BorrowStatusEntity,Long> {


}
