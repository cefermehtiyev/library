package az.ingress.service.abstraction;

import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.model.enums.CommonStatus;
import az.ingress.model.request.CommonStatusRequest;

public interface CommonStatusService {
    void addStatus(CommonStatus status);
    Long getCount();
    CommonStatusEntity getCommonStatusEntity(Long id);
}
