package az.ingress.dao.entity;

import az.ingress.model.enums.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;



import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "admins")
@Entity
public class AdminEntity {

    @Id
    private Long id;
    @Enumerated(STRING)
    private AdminRole adminRole;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @ToString.Exclude
    UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AdminEntity that = (AdminEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
