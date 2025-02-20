package az.ingress.service.concurate;

import az.ingress.dao.entity.BorrowStatusEntity;
import az.ingress.dao.repository.BorrowStatusRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.BorrowStatusMapper;
import az.ingress.model.enums.BorrowStatus;
import az.ingress.model.request.BorrowRequest;
import az.ingress.model.request.BorrowStatusRequest;
import az.ingress.service.abstraction.BorrowStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.BorrowStatusMapper.BORROW_STATUS_MAPPER;

@Service
@RequiredArgsConstructor
public class BorrowStatusServiceHandler implements BorrowStatusService {
    private final BorrowStatusRepository borrowStatusRepository;

    @Override
    public void addStatus(BorrowStatus borrowStatus) {
        borrowStatusRepository.save(BORROW_STATUS_MAPPER.buildBorrowStatusEntity(borrowStatus));
    }

    @Override
    public Long getCount() {
        return borrowStatusRepository.count();
    }

    @Override
    public BorrowStatusEntity getBorrowStatus(Long id) {
        return fetchEntityExist(id);
    }

    private BorrowStatusEntity fetchEntityExist(Long id){
        return borrowStatusRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BORROW_STATUS_NOT_FOUND.getMessage())
        );
    }
}
