package azmiu.library.dao.entity;

import azmiu.library.model.enums.CommonStatus;
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

import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "common_statuses")
@Entity
public class CommonStatusEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Enumerated(STRING)
    CommonStatus status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommonStatusEntity that = (CommonStatusEntity) o;
        return Objects.equals(id, that.id) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}
