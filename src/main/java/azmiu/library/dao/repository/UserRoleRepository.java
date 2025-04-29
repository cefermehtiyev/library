package azmiu.library.dao.repository;

import azmiu.library.dao.entity.UserRoleEntity;
import azmiu.library.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByRoleName(RoleName roleName);
}
