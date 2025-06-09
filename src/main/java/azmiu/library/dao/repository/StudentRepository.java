package azmiu.library.dao.repository;

import azmiu.library.dao.entity.StudentEntity;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@NonNullApi
public interface StudentRepository extends JpaRepository<StudentEntity,Long>, JpaSpecificationExecutor<StudentEntity> {

    @EntityGraph(attributePaths ={
            "user",
            "user.commonStatus",
            "user.userRole"
    }
    )
    Page<StudentEntity> findAll(@NonNull Specification<StudentEntity>spec, @NonNull Pageable pageable);


}
