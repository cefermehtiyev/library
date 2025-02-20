package az.ingress.service.concurate;

import az.ingress.dao.entity.InventoryStatusEntity;
import az.ingress.dao.repository.InventoryStatusRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.InventoryStatusMapper;
import az.ingress.model.enums.InventoryStatus;
import az.ingress.model.request.InventoryStatusRequest;
import az.ingress.service.abstraction.InventoryStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.InventoryStatusMapper.INVENTORY_STATUS_MAPPER;

@Service
@RequiredArgsConstructor
public class InventoryStatusServiceHandler implements InventoryStatusService {
    private final InventoryStatusRepository inventoryStatusRepository;


    @Override
    public void addStatus(InventoryStatus status) {
        inventoryStatusRepository.save(INVENTORY_STATUS_MAPPER.buildInventoryStatus(status));
    }

    @Override
    public Long getCount() {
        return inventoryStatusRepository.count();
    }

    @Override
    public InventoryStatusEntity getInventoryEntity(Long id) {
        return fetchByExist(id);
    }

    private InventoryStatusEntity fetchByExist(Long id){
        return inventoryStatusRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.INVENTORY_STATUS_NOT_FOUND.getMessage())
        );
    }
}
