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

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface BookRepository extends JpaRepository<BookEntity, Long>,JpaSpecificationExecutor<BookEntity>{
    @Query("SELECT b FROM BookEntity b WHERE b.bookCode = :bookCode AND b.commonStatus.status = 'ACTIVE'")
    Optional<BookEntity> findActiveBookByBookCode(String bookCode);

    @Query("SELECT b FROM BookEntity b WHERE b.bookCode = :bookCode AND b.commonStatus.status = 'INACTIVE'")
    Optional<BookEntity> findInActiveBookByBookCode(String bookCode);

    Optional<BookEntity> findByBookCode(String bookCode);


    @Query("SELECT b.bookInventory FROM BookEntity b WHERE b.title = :title AND b.author = :author AND b.publicationYear = :publicationYear")
    Optional<BookInventoryEntity> findFirstByTitleAndAuthorAndPublicationYear(String title, String author, Integer publicationYear);

    @EntityGraph(attributePaths = {
            "bookInventory",
            "bookInventory.ratingDetails",
            "bookInventory.file",
            "bookInventory.image",
            "bookInventory.category",
            "bookInventory.inventoryStatus",
            "commonStatus"
    })
    Optional<BookEntity> findById(Long id);


    @EntityGraph(attributePaths = {
            "bookInventory",
            "bookInventory.ratingDetails",
            "bookInventory.file",
            "bookInventory.image",
            "bookInventory.category",
            "bookInventory.inventoryStatus",
            "commonStatus"
    })
    Page<BookEntity> findAll(Specification<BookEntity> spec, Pageable pageable);






    @Query("SELECT b FROM BookEntity b WHERE b.id IN (SELECT MIN(b2.id) FROM BookEntity b2 GROUP BY b2.title)")
    Page<BookEntity> findAllDistinct(Specification<BookEntity> spec, Pageable pageable);


    boolean existsByBookCode(String bookCode);


}

