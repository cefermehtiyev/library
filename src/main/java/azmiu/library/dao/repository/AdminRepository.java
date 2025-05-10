package azmiu.library.dao.repository;

import azmiu.library.dao.entity.AdminEntity;
import azmiu.library.dao.entity.UserEntity;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@NonNullApi
public interface AdminRepository extends JpaRepository<AdminEntity,Long> {


    @EntityGraph(attributePaths = {
            "user",
            "user.commonStatus",
            "user.userRole"
    })
    Page<AdminEntity> findAll(@NonNull Specification<UserEntity> spec, @NonNull Pageable pageable);


}
