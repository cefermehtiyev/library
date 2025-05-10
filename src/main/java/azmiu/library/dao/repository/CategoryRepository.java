package azmiu.library.dao.repository;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.CategoryEntity;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@NonNullApi
public interface CategoryRepository extends JpaRepository<CategoryEntity ,Long>, JpaSpecificationExecutor<CategoryEntity> {
    boolean existsByBookCategory(String bookCategory);

    @EntityGraph(attributePaths = {
            "commonStatus",
            "image"
    })
    Page<CategoryEntity> findAll( Specification<CategoryEntity> spec, Pageable pageable);

}
