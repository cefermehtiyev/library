package azmiu.library.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class EmployeeEntity {
    @Id
    Long id;
    String department;
    String position;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @ToString.Exclude
    UserEntity user;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity employeeEntity = (EmployeeEntity) o;
        return Objects.equals(id, employeeEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
