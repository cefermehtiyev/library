package azmiu.library.service.concurate;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.UserCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.AdminRepository;
import azmiu.library.model.request.AdminRequest;
import azmiu.library.model.response.AdminResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserResponse;
import azmiu.library.service.abstraction.AdminService;
import azmiu.library.service.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.AdminMapper.ADMIN_MAPPER;
import static azmiu.library.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AdminServiceHandler implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public void addAdmin(UserEntity userEntity, AdminRequest adminRequest) {
        var admin = ADMIN_MAPPER.buildAdminEntity(userEntity,adminRequest);
        adminRepository.save(admin);
    }

    @Override
    public PageableResponse<AdminResponse> getAllAdmins(PageCriteria pageCriteria, UserCriteria userCriteria) {
        var userPage = adminRepository.findAll(
                new UserSpecification(userCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );

        return ADMIN_MAPPER.pageableStudentResponse(userPage);
    }
}
