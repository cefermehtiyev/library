package azmiu.library.dao.repository;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> , JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUserName(String userName);



    @Query(value = "SELECT b from BookEntity b JOIN b.users u WHERE u.fin = :fin ")
    Page<BookEntity> findBooksByFin(String fin, Pageable pageable);

}
