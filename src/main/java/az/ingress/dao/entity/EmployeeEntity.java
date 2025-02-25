package az.ingress.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntity {
    @Id
    private Long id;
    private String department;
    private String position;

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
