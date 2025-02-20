package az.ingress.mapper;

import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.model.enums.CommonStatus;
import az.ingress.model.request.CommonStatusRequest;

public enum CommonStatusMapper {
    COMMON_STATUS_MAPPER;

    public CommonStatusEntity buildCommonStatusEntity (CommonStatusRequest commonStatusRequest){
        return CommonStatusEntity.builder()
                .status(commonStatusRequest.getCommonStatus())
                .build();
    }

    public CommonStatusRequest buildCommonStatusRequest(CommonStatus status){
        return CommonStatusRequest.builder().commonStatus(status).build();
    }
}
