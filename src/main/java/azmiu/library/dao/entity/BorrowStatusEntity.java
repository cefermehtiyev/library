package azmiu.library.dao.entity;

import azmiu.library.model.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrow_statuses")
@Builder
@FieldDefaults(level = PRIVATE)
public class BorrowStatusEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    Long id;
    @Enumerated(STRING)
    BorrowStatus status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BorrowStatusEntity that = (BorrowStatusEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
