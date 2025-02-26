package az.ingress.service.concurate;

import az.ingress.configuration.UserRoleConfig;
import az.ingress.dao.entity.UserRoleEntity;
import az.ingress.dao.repository.UserRoleRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.UserRoleMapper;
import az.ingress.model.enums.RoleName;
import az.ingress.service.abstraction.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.exception.ErrorMessage.USER_ROLE_NOT_FUND;
import static az.ingress.mapper.UserRoleMapper.USER_ROLE_MAPPER;

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
            default -> throw new NotFoundException(USER_ROLE_NOT_FUND.getMessage());
        };
    }

    private UserRoleEntity fetchEntityExist(Long id){
        return userRoleRepository.findById(id).orElseThrow(
                () -> new NotFoundException(USER_ROLE_NOT_FUND.getMessage())
        );
    }
}
