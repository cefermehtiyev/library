package azmiu.library.dao.repository;

import azmiu.library.dao.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
}
