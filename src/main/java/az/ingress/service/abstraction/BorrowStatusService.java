package az.ingress.service.abstraction;

import az.ingress.dao.entity.BorrowStatusEntity;
import az.ingress.model.enums.BorrowStatus;
import az.ingress.model.request.BorrowStatusRequest;

public interface BorrowStatusService {
    void addStatus(BorrowStatus status);
    Long getCount();
    BorrowStatusEntity getBorrowStatus(Long id);
}
