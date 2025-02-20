package az.ingress.service.abstraction;

import az.ingress.dao.entity.BorrowStatusEntity;
import az.ingress.model.request.BorrowStatusRequest;

public interface BorrowStatusService {
    void addStatus(BorrowStatusRequest request);
    BorrowStatusEntity getBorrowStatus(Long id);
}
