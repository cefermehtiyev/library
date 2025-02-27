package azmiu.library.dao.repository;

import azmiu.library.dao.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity,Long> {
}
