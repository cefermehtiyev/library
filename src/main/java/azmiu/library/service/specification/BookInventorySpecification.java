package azmiu.library.service.specification;

import azmiu.library.criteria.BookCriteria;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.model.constants.CriteriaConstants;
import azmiu.library.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static azmiu.library.model.constants.CriteriaConstants.LANGUAGE;
import static azmiu.library.model.constants.CriteriaConstants.PUBLICATION_YEAR;
import static azmiu.library.model.constants.CriteriaConstants.READ_COUNT;
import static azmiu.library.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class BookInventorySpecification implements Specification<BookInventoryEntity> {
    private final BookCriteria bookCriteria;

    @Override
    public Predicate toPredicate(Root<BookInventoryEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var bookJoin = root.join("books", JoinType.INNER);

        var predicates = PredicateUtil.builder()
                .addNullSafety(bookCriteria.getTitle(),
                        title -> cb.like(bookJoin.get(CriteriaConstants.TITLE), applyLikePattern(title)))
                .addNullSafety(bookCriteria.getAuthor(),
                        author -> cb.like(bookJoin.get(CriteriaConstants.AUTHOR), applyLikePattern(author)))
                .addNullSafety(
                        bookCriteria.getPublicationYearFrom(),
                        publicationYearFrom -> cb.greaterThanOrEqualTo(bookJoin.get(PUBLICATION_YEAR), publicationYearFrom))
                .addNullSafety(
                        bookCriteria.getPublicationYearTo(),
                        publicationYearTo -> cb.lessThanOrEqualTo(bookJoin.get(PUBLICATION_YEAR), publicationYearTo))
                .addNullSafety(
                        bookCriteria.getLanguage(), language -> cb.like(bookJoin.get(LANGUAGE), applyLikePattern(language)))
                .addNullSafety(bookCriteria.getReadCountFrom(),
                        readCountFrom -> cb.greaterThanOrEqualTo(root.get(READ_COUNT),readCountFrom)
                        )
                .addNullSafety(bookCriteria.getReadCountTo(),
                        readCountTo -> cb.lessThanOrEqualTo(root.get(READ_COUNT),readCountTo)
                        )
                .build();

        return cb.and(predicates);
    }
}
