package az.ingress.dao.repository;

import az.ingress.dao.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
}
