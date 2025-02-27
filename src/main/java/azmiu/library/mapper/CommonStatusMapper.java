package azmiu.library.mapper;

import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.request.CommonStatusRequest;

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
