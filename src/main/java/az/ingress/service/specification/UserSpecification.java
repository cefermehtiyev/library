package az.ingress.service.specification;

import az.ingress.criteria.UserCriteria;
import az.ingress.dao.entity.UserEntity;
import az.ingress.util.PredicateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import static az.ingress.model.constants.CriteriaConstants.USER_NAME;

@AllArgsConstructor
public class UserSpecification implements Specification<UserEntity> {
    private final UserCriteria userCriteria;


    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = PredicateUtil.builder().addNullSafety(
                userCriteria.getUserName(), userName -> criteriaBuilder.like(root.get(USER_NAME),PredicateUtil.applyLikePattern(userName))
        ).build();
        return criteriaBuilder.and(predicates);
    }
}
