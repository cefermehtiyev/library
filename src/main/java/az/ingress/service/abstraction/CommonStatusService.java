package az.ingress.service.abstraction;

import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.model.request.CommonStatusRequest;

public interface CommonStatusService {
    void addStatus(CommonStatusRequest statusRequest);
    CommonStatusEntity getCommonStatusEntity(Long id);
}
