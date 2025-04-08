package azmiu.library.service.specification;

import azmiu.library.criteria.EmployeeCriteria;
import azmiu.library.dao.entity.EmployeeEntity;
import azmiu.library.model.constants.CriteriaConstants;
import azmiu.library.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static azmiu.library.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class EmployeeSpecification implements Specification<EmployeeEntity> {
    private final EmployeeCriteria employeeCriteria;

    @Override
    public Predicate toPredicate(Root<EmployeeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var userJoin = root.join("user", JoinType.INNER);

        var predicates = PredicateUtil.builder()
                .addNullSafety(
                        employeeCriteria.getUserName(),
                        userName -> cb.like(userJoin.get(CriteriaConstants.USER_NAME), applyLikePattern(userName))
                )
                .addNullSafety(
                        employeeCriteria.getDepartment(),
                        department -> cb.like(root.get(CriteriaConstants.DEPARTMENT), applyLikePattern(department))
                ).addNullSafety(
                        employeeCriteria.getPosition(),
                        position -> cb.like(root.get(CriteriaConstants.POSITION),applyLikePattern(position))
                )
                .build();

        return cb.and(predicates);

    }
}
