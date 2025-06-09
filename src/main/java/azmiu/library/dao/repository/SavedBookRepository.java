package azmiu.library.dao.repository;

import azmiu.library.dao.entity.SavedBookEntity;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedBookRepository extends JpaRepository<SavedBookEntity,Long> {
    boolean existsByUserIdAndBookInventoryId(Long userId, Long inventoryId);

    @EntityGraph(attributePaths = {
            "bookInventory",
            "bookInventory.books",
            "bookInventory.ratingDetails",
            "bookInventory.file",
            "bookInventory.image",
            "bookInventory.category",
            "bookInventory.inventoryStatus",
            "bookInventory.commonStatus"
    })
    Page<SavedBookEntity> findAllByUserId( Long id, Pageable pageable);
}
