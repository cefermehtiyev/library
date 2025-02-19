package az.ingress.mapper;

import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.model.request.CommonStatusRequest;

public enum CommonStatusMapper {
    COMMON_STATUS_MAPPER;

    public CommonStatusEntity buildCommonStatusEntity (CommonStatusRequest commonStatusRequest){
        return CommonStatusEntity.builder()
                .statusType(commonStatusRequest.getCommonStatus())
                .build();
    }
}
