package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BorrowStatusEntity;
import azmiu.library.model.enums.BorrowStatus;

public interface BorrowStatusService {
    void addStatus(BorrowStatus status);
    Long getCount();
    BorrowStatusEntity getBorrowStatus(BorrowStatus status);

}
