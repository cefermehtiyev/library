package azmiu.library.dao.repository;

import azmiu.library.dao.entity.InventoryStatusEntity;
import azmiu.library.model.enums.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryStatusRepository extends JpaRepository<InventoryStatusEntity,Long> {
    Optional<InventoryStatusEntity> findByStatus(InventoryStatus status);
}
