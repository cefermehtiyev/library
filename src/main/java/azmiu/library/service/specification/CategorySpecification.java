package azmiu.library.service.specification;

import azmiu.library.criteria.CategoryCriteria;
import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.constants.CriteriaConstants;
import azmiu.library.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static azmiu.library.model.constants.CriteriaConstants.USER_NAME;
import static azmiu.library.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class CategorySpecification implements Specification<CategoryEntity> {
    private final CategoryCriteria categoryCriteria;


    @Override
    public Predicate toPredicate(Root<CategoryEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates = PredicateUtil.builder()
                .addNullSafety(categoryCriteria.getBookCategory(),
                        bookCategory -> cb.like(root.get(CriteriaConstants.BOOK_CATEGORY),applyLikePattern(bookCategory)))
                .build();

        return cb.and(predicates);
    }
}
