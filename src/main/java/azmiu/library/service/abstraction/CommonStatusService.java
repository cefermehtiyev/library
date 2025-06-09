package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.model.enums.CommonStatus;

public interface CommonStatusService {
    void addStatus(CommonStatus status);
    Long getCount();
    CommonStatusEntity getCommonStatusEntity(CommonStatus status);
}
