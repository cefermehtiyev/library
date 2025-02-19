package az.ingress.service.concurate;

import az.ingress.dao.entity.CommonStatusEntity;
import az.ingress.dao.repository.CommonStatusRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.request.CommonStatusRequest;
import az.ingress.service.abstraction.CommonStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.CommonStatusMapper.COMMON_STATUS_MAPPER;

@Service
@RequiredArgsConstructor
public class CommonStatusServiceHandler implements CommonStatusService {
    private final CommonStatusRepository commonStatusRepository;

    @Override
    public void addStatus(CommonStatusRequest statusRequest) {
        commonStatusRepository.save(COMMON_STATUS_MAPPER.buildCommonStatusEntity(statusRequest));
    }

    @Override
    public CommonStatusEntity getCommonStatusEntity(Long id){
        return fetchEntityExist(id);
    }

    private CommonStatusEntity fetchEntityExist(Long id){
        return commonStatusRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.COMMON_STATUS_NOT_FOUND.getMessage())
        );
    }

}
