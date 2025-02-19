package az.ingress.dao.repository;

import az.ingress.dao.entity.InventoryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryStatusRepository extends JpaRepository<InventoryStatusEntity,Long> {
}
