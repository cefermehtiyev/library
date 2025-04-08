package azmiu.library.dao.repository;

import azmiu.library.dao.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<StudentEntity,Long>, JpaSpecificationExecutor<StudentEntity> {
}
