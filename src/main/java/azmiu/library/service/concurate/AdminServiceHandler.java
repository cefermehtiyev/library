package azmiu.library.service.concurate;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.AdminRepository;
import azmiu.library.model.request.AdminRequest;
import azmiu.library.service.abstraction.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.AdminMapper.ADMIN_MAPPER;

@Service
@RequiredArgsConstructor
public class AdminServiceHandler implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public void addAdmin(UserEntity userEntity, AdminRequest adminRequest) {
        var admin = ADMIN_MAPPER.buildAdminEntity(adminRequest);
        admin.setUser(userEntity);
        adminRepository.save(admin);
    }
}
