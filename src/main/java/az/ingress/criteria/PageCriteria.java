package az.ingress.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.criteria.CriteriaBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageCriteria {
    @JsonInclude(USE_DEFAULTS)
    private Integer page = 0;
    @JsonInclude(USE_DEFAULTS)
    private Integer count = 10;
}
