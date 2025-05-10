package azmiu.library.dao.repository;

import azmiu.library.dao.entity.AdminEntity;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.entity.UserRoleEntity;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@NonNullApi
public interface UserRepository extends JpaRepository<UserEntity,Long> , JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUserName(String userName);

    @EntityGraph(attributePaths = {
            "student",
            "userRole",
            "admin",
            "commonStatus",
            "employee"
        })
    Page<UserEntity> findAll(@NonNull Specification<UserEntity> spec, @NonNull Pageable pageable);



}
