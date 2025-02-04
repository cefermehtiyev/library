package az.ingress.service.specification;

import az.ingress.criteria.BookCriteria;
import az.ingress.dao.entity.BookEntity;
import az.ingress.model.constants.CriteriaConstants;
import az.ingress.util.PredicateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static az.ingress.model.constants.CriteriaConstants.AUTHOR;
import static az.ingress.model.constants.CriteriaConstants.LANGUAGE;
import static az.ingress.model.constants.CriteriaConstants.PUBLICATION_YEAR;
import static az.ingress.model.constants.CriteriaConstants.READ_COUNT;
import static az.ingress.model.constants.CriteriaConstants.TITLE;
import static az.ingress.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class BookSpecification implements Specification<BookEntity> {
    private final BookCriteria bookCriteria;

    @Override
    public Predicate toPredicate(Root<BookEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = PredicateUtil.builder().addNullSafety(
                bookCriteria.getTitle(),title -> criteriaBuilder.like(root.get(TITLE),applyLikePattern(title))
        ).addNullSafety(
                bookCriteria.getAuthor(),author -> criteriaBuilder.like(root.get(AUTHOR),applyLikePattern(author))
        ).addNullSafety(
                bookCriteria.getPublicationYearFrom(),
                publicationYearFrom -> criteriaBuilder.greaterThanOrEqualTo(root.get(PUBLICATION_YEAR),publicationYearFrom)
        ).addNullSafety(
                bookCriteria.getPublicationYearTo(),
                publicationYearTo -> criteriaBuilder.lessThanOrEqualTo(root.get(PUBLICATION_YEAR),publicationYearTo)
        ).addNullSafety(
                bookCriteria.getLanguage(),language -> criteriaBuilder.like(root.get(LANGUAGE),applyLikePattern(language))
        ).build();

        return criteriaBuilder.and(predicates);
    }




}
