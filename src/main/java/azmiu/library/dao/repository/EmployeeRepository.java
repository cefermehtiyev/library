package azmiu.library.dao.repository;

import azmiu.library.dao.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
}
