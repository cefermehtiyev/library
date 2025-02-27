package azmiu.library.service.specification;

import azmiu.library.criteria.AuthorCriteria;
import azmiu.library.dao.entity.AuthorEntity;
import azmiu.library.util.PredicateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import static azmiu.library.model.constants.CriteriaConstants.AUTHOR_NAME;

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
