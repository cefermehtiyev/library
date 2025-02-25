package az.ingress.service.specification;

import az.ingress.criteria.AuthorCriteria;
import az.ingress.dao.entity.AuthorEntity;
import az.ingress.dao.entity.UserEntity;
import az.ingress.util.PredicateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import static az.ingress.model.constants.CriteriaConstants.AUTHOR_NAME;
import static az.ingress.model.constants.CriteriaConstants.USER_NAME;

@AllArgsConstructor
public class AuthorSpecification implements Specification<AuthorEntity> {
    private final AuthorCriteria authorCriteria;

    @Override
    public Predicate toPredicate(Root<AuthorEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = PredicateUtil.builder().addNullSafety(
                authorCriteria.getName(), name -> criteriaBuilder.like(root.get(AUTHOR_NAME),PredicateUtil.applyLikePattern(name))
        ).build();
        return criteriaBuilder.and(predicates);
    }
}
