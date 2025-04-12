package azmiu.library.service.specification;

import azmiu.library.criteria.BookCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.util.PredicateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import static azmiu.library.model.constants.CriteriaConstants.AUTHOR;
import static azmiu.library.model.constants.CriteriaConstants.LANGUAGE;
import static azmiu.library.model.constants.CriteriaConstants.PUBLICATION_YEAR;
import static azmiu.library.model.constants.CriteriaConstants.TITLE;
import static azmiu.library.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class BookSpecification implements Specification<BookEntity> {
    private final BookCriteria bookCriteria;

    @Override
    public Predicate toPredicate(Root<BookEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates = PredicateUtil.builder().addNullSafety(
                bookCriteria.getTitle(),title -> cb.like(root.get(TITLE),applyLikePattern(title))
        ).addNullSafety(
                bookCriteria.getAuthor(),author -> cb.like(root.get(AUTHOR),applyLikePattern(author))
        ).addNullSafety(
                bookCriteria.getPublicationYearFrom(),
                publicationYearFrom -> cb.greaterThanOrEqualTo(root.get(PUBLICATION_YEAR),publicationYearFrom)
        ).addNullSafety(
                bookCriteria.getPublicationYearTo(),
                publicationYearTo -> cb.lessThanOrEqualTo(root.get(PUBLICATION_YEAR),publicationYearTo)
        ).addNullSafety(
                bookCriteria.getLanguage(),language -> cb.like(root.get(LANGUAGE),applyLikePattern(language))
        ).build();

        return cb.and(predicates);
    }


}
