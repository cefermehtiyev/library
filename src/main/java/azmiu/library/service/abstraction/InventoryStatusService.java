package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.InventoryStatusEntity;
import azmiu.library.model.enums.InventoryStatus;

public interface InventoryStatusService {
    void addStatus(InventoryStatus status);
    Long getCount();
    InventoryStatusEntity getInventoryEntityStatus(InventoryStatus inventoryStatus);

}
