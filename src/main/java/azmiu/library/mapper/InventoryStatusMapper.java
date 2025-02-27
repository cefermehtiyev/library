package azmiu.library.mapper;

import azmiu.library.dao.entity.InventoryStatusEntity;
import azmiu.library.model.enums.InventoryStatus;
import azmiu.library.model.request.InventoryStatusRequest;

public enum InventoryStatusMapper {
    INVENTORY_STATUS_MAPPER;

    public InventoryStatusEntity buildInventoryStatus(InventoryStatus status){
        return InventoryStatusEntity.builder().status(status).build();
    }

    public InventoryStatusRequest buildInventoryRequest(InventoryStatus status){
        return InventoryStatusRequest.builder().inventoryStatus(status).build();
    }
}
