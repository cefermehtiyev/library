package azmiu.library.dao.repository;

import azmiu.library.dao.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<FileEntity,Long> {


}
