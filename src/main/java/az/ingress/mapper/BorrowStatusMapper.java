package az.ingress.mapper;

import az.ingress.dao.entity.BorrowStatusEntity;
import az.ingress.model.enums.BorrowStatus;
import az.ingress.model.request.BorrowRequest;
import az.ingress.model.request.BorrowStatusRequest;

public enum BorrowStatusMapper {
    BORROW_STATUS_MAPPER;

    public BorrowStatusEntity buildBorrowStatusEntity(BorrowStatus status){
        return BorrowStatusEntity.builder()
                .status(status)
                .build();
    }

    public BorrowStatusRequest buildBorrowStatusRequest(BorrowStatus status){
        return BorrowStatusRequest.builder()
                .borrowStatus(status)
                .build();

    }
}
