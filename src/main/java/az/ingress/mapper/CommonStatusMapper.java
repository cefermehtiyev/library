package az.ingress.mapper;

import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.model.enums.CommonStatus;
import az.ingress.model.request.CommonStatusRequest;

public enum CommonStatusMapper {
    COMMON_STATUS_MAPPER;

    public CommonStatusEntity buildCommonStatusEntity (CommonStatus status){
        return CommonStatusEntity.builder()
                .status(status)
                .build();
    }

    public CommonStatusRequest buildCommonStatusRequest(CommonStatus status){
        return CommonStatusRequest.builder().commonStatus(status).build();
    }
}
