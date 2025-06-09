package azmiu.library.service.concurate;

import azmiu.library.dao.entity.UserRoleEntity;
import azmiu.library.dao.repository.UserRoleRepository;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.enums.RoleName;
import azmiu.library.service.abstraction.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static azmiu.library.exception.ErrorMessage.USER_ROLE_NOT_FOUND;
import static azmiu.library.mapper.UserRoleMapper.USER_ROLE_MAPPER;
import static azmiu.library.model.enums.RoleName.ADMIN;
import static azmiu.library.model.enums.RoleName.EMPLOYEE;
import static azmiu.library.model.enums.RoleName.STUDENT;
import static azmiu.library.model.enums.RoleName.SUPER_ADMIN;

@Service
@RequiredArgsConstructor
public class UserRoleServiceHandler implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

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
            case ADMIN -> findByRoleName(ADMIN);
            case STUDENT -> findByRoleName(STUDENT);
            case EMPLOYEE -> findByRoleName(EMPLOYEE);
            case SUPER_ADMIN -> findByRoleName(SUPER_ADMIN);
        };
    }

    private UserRoleEntity findByRoleName(RoleName roleName){
        return userRoleRepository.findByRoleName(roleName).orElseThrow(
                () -> new NotFoundException(USER_ROLE_NOT_FOUND.getMessage())
        );
    }
}
