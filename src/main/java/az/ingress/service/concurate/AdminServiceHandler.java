package az.ingress.service.concurate;

import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.AdminRepository;
import az.ingress.mapper.AdminMapper;
import az.ingress.model.request.AdminRequest;
import az.ingress.service.abstraction.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.AdminMapper.ADMIN_MAPPER;

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
