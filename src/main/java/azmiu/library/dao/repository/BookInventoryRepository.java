package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
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
public interface BookInventoryRepository extends JpaRepository<BookInventoryEntity, Long>, JpaSpecificationExecutor<BookInventoryEntity> {

    @EntityGraph(attributePaths = {
            "books",
            "books.commonStatus",
            "ratingDetails",
            "commonStatus",
            "inventoryStatus",
            "file",
            "image",
            "category"
    }
    )
    Page<BookInventoryEntity> findAll(@NonNull Specification<BookInventoryEntity> spec, @NonNull Pageable pageable);


}

