package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookInventoryRepository extends JpaRepository<BookInventoryEntity, Long>, JpaSpecificationExecutor<BookInventoryEntity> {
    Optional<BookInventoryEntity> findByTitleAndPublicationYear(String title, Integer publicationYear);


}

