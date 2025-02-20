package az.ingress.model.request;

import az.ingress.model.enums.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStatusRequest {
    private InventoryStatus inventoryStatus;
}
