package azmiu.library.mapper;

import azmiu.library.dao.entity.BorrowStatusEntity;
import azmiu.library.model.enums.BorrowStatus;
import azmiu.library.model.request.BorrowStatusRequest;

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
