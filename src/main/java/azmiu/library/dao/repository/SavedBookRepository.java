package azmiu.library.dao.repository;

import azmiu.library.dao.entity.SavedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedBookRepository extends JpaRepository<SavedBookEntity,Long> {
}
