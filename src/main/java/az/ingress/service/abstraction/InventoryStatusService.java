package az.ingress.service.abstraction;

import az.ingress.dao.entity.InventoryStatusEntity;
import az.ingress.model.request.InventoryStatusRequest;

public interface InventoryStatusService {
    void AddStatus(InventoryStatusRequest request);
    InventoryStatusEntity getInventoryEntity(Long id);
}
