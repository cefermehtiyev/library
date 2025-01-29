package az.ingress.dao.repository;

import az.ingress.dao.entity.BookInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookInventoryRepository extends JpaRepository<BookInventoryEntity,Long> {
    Optional<BookInventoryEntity> findByTitleAndPublicationYear(String title, Integer publicationYear);
}

