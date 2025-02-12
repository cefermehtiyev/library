package az.ingress.service.specification;

import az.ingress.criteria.UserCriteria;
import az.ingress.dao.entity.UserEntity;
import az.ingress.util.PredicateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
