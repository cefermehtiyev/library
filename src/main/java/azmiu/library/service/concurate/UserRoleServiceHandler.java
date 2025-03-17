package azmiu.library.service.concurate;

import azmiu.library.configuration.UserRoleConfig;
import azmiu.library.dao.entity.UserRoleEntity;
import azmiu.library.dao.repository.UserRoleRepository;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.enums.RoleName;
import azmiu.library.service.abstraction.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.exception.ErrorMessage.USER_ROLE_NOT_FUND;
import static azmiu.library.mapper.UserRoleMapper.USER_ROLE_MAPPER;

@Service
@RequiredArgsConstructor
public class UserRoleServiceHandler implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleConfig userRoleConfig;

    @Override
    public void addRole(RoleName roleName) {
        userRoleRepository.save(USER_ROLE_MAPPER.buildUserRoleEntity(roleName));
    }

    @Override
    public Long getCount() {
        return userRoleRepository.count();
    }

    @Override
    public UserRoleEntity getUserRole(RoleName roleName) {
        return switch (roleName) {
            case ADMIN -> fetchEntityExist(userRoleConfig.getAdmin());
            case STUDENT -> fetchEntityExist(userRoleConfig.getStudent());
            case EMPLOYEE -> fetchEntityExist(userRoleConfig.getEmployee());
            case SUPER_ADMIN -> fetchEntityExist(userRoleConfig.getSuperAdmin());
        };
    }

    private UserRoleEntity fetchEntityExist(Long id){
        return userRoleRepository.findById(id).orElseThrow(
                () -> new NotFoundException(USER_ROLE_NOT_FUND.getMessage())
        );
    }
}
