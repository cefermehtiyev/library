package az.ingress.dao.repository;

import az.ingress.dao.entity.BookInventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookInventoryRepository extends JpaRepository<BookInventoryEntity,Long> {
    Optional<BookInventoryEntity> findByTitleAndPublicationYear(String title, Integer publicationYear);
    List<BookInventoryEntity> findAllByOrderByReadCountDesc();


}

