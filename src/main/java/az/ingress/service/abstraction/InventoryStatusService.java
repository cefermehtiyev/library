package az.ingress.service.abstraction;

import az.ingress.dao.entity.InventoryStatusEntity;
import az.ingress.model.enums.InventoryStatus;
import az.ingress.model.request.InventoryStatusRequest;

public interface InventoryStatusService {
    void addStatus(InventoryStatus status);
    Long getCount();
    InventoryStatusEntity getInventoryEntity(Long id);
}
