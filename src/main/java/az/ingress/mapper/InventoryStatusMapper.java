package az.ingress.mapper;

import az.ingress.dao.entity.InventoryStatusEntity;
import az.ingress.model.enums.InventoryStatus;
import az.ingress.model.request.InventoryStatusRequest;
import az.ingress.service.abstraction.InventoryStatusService;

public enum InventoryStatusMapper {
    INVENTORY_STATUS_MAPPER;

    public InventoryStatusEntity buildInventoryStatus(InventoryStatusRequest request){
        return InventoryStatusEntity.builder().status(request.getInventoryStatus()).build();
    }

    public InventoryStatusRequest buildInventoryRequest(InventoryStatus status){
        return InventoryStatusRequest.builder().inventoryStatus(status).build();
    }
}
