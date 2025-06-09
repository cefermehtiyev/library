package azmiu.library.service.concurate;

import azmiu.library.dao.entity.BorrowStatusEntity;
import azmiu.library.dao.repository.BorrowStatusRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.enums.BorrowStatus;
import azmiu.library.service.abstraction.BorrowStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.BorrowStatusMapper.BORROW_STATUS_MAPPER;

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
    public BorrowStatusEntity getBorrowStatus(BorrowStatus borrowStatus) {
        return findByStatus(borrowStatus);
    }


    private BorrowStatusEntity findByStatus(BorrowStatus borrowStatus){
        return borrowStatusRepository.findByStatus(borrowStatus).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BORROW_STATUS_NOT_FOUND.getMessage())
        );
    }
}
