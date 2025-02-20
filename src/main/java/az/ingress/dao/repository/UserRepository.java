package az.ingress.dao.repository;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> , JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByEmail(String email);


    Optional<UserEntity> findByFin(String fin);

    @Query(value = "SELECT b from BookEntity b JOIN b.userEntities u WHERE u.fin = :fin ")
    Page<BookEntity> findBooksByFin(String fin, Pageable pageable);

    @Query("SELECT b FROM BookEntity b " +
            "JOIN b.bookInventoryEntity bi " +
            "WHERE b.id = (SELECT MIN(b2.id) FROM BookEntity b2 WHERE b2.title = b.title)"+
            "ORDER BY " +
            "CASE WHEN :sortBy = 'createAt' AND :order = 'asc' THEN bi.createdAt END ASC,"+
            "CASE WHEN :sortBy = 'createdAt' AND :order = 'desc'THEN bi.createdAt END DESC,"+
            "CASE WHEN :sortBy = 'readCount' AND :order = 'asc' THEN bi.readCount END ASC ,"+
            "CASE WHEN :sortBy = 'readCount' AND :order = 'desc' THEN bi.readCount END DESC "
    )
    Page<BookEntity> findDistinctBooks(@Param("sortBy")String sortBy, @Param("order") String order, org.springframework.data.domain.Pageable pageable);

}
