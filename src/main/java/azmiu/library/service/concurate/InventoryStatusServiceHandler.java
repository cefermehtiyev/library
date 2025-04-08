package azmiu.library.service.concurate;

import azmiu.library.dao.entity.InventoryStatusEntity;
import azmiu.library.dao.repository.InventoryStatusRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.enums.InventoryStatus;
import azmiu.library.service.abstraction.InventoryStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.InventoryStatusMapper.INVENTORY_STATUS_MAPPER;

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
    public InventoryStatusEntity getInventoryEntityStatus(Long id) {
        return fetchByExist(id);
    }

    private InventoryStatusEntity fetchByExist(Long id){
        return inventoryStatusRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.INVENTORY_STATUS_NOT_FOUND.getMessage())
        );
    }
}
