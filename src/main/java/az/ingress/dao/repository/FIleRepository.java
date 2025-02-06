package az.ingress.dao.repository;

import az.ingress.dao.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FIleRepository extends JpaRepository<FileEntity,Long> {
}
