package azmiu.library.service.concurate;

import azmiu.library.dao.entity.CommonStatusEntity;
import azmiu.library.dao.repository.CommonStatusRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.enums.CommonStatus;
import azmiu.library.service.abstraction.CommonStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.CommonStatusMapper.COMMON_STATUS_MAPPER;

@Service
@RequiredArgsConstructor
public class CommonStatusServiceHandler implements CommonStatusService {
    private final CommonStatusRepository commonStatusRepository;

    @Override
    public void addStatus(CommonStatus status) {
        commonStatusRepository.save(COMMON_STATUS_MAPPER.buildCommonStatusEntity(status));
    }

    @Override
    public Long getCount() {
        return commonStatusRepository.count();
    }

    @Override
    public CommonStatusEntity getCommonStatusEntity(CommonStatus status){
        return findByStatus(status);
    }


    private CommonStatusEntity findByStatus(CommonStatus status){
        return commonStatusRepository.findByStatus(status).orElseThrow(
                () -> new NotFoundException(ErrorMessage.COMMON_STATUS_NOT_FOUND.getMessage())
        );
    }

}
