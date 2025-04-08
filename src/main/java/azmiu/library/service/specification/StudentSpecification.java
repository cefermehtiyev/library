package azmiu.library.service.specification;

import azmiu.library.criteria.StudentCriteria;
import azmiu.library.dao.entity.StudentEntity;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.constants.CriteriaConstants;
import azmiu.library.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static azmiu.library.model.constants.CriteriaConstants.GROUP_NAME;
import static azmiu.library.model.constants.CriteriaConstants.SPECIALIZATION;
import static azmiu.library.model.constants.CriteriaConstants.USER_NAME;
import static azmiu.library.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class StudentSpecification implements Specification<StudentEntity> {

    private final StudentCriteria studentCriteria;

    @Override
    public Predicate toPredicate(Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var userJoin = root.join("user", JoinType.INNER);

        var predicates = PredicateUtil.builder()
                .addNullSafety(
                        studentCriteria.getUserName(),
                        userName -> cb.like(userJoin.get(USER_NAME), applyLikePattern(userName))
                )
                .addNullSafety(
                        studentCriteria.getSpecialization(),
                        specialization -> cb.like(root.get(SPECIALIZATION), applyLikePattern(specialization))
                ).addNullSafety(
                        studentCriteria.getGroupName(),
                        groupName -> cb.like(root.get(GROUP_NAME),applyLikePattern(groupName))
                ).addNullSafety(
                        studentCriteria.getCourseFrom(),
                        courseFrom -> cb.greaterThanOrEqualTo(root.get(CriteriaConstants.COURSE),courseFrom)
                ).addNullSafety(
                        studentCriteria.getCourseTo(),
                        courseTo -> cb.lessThanOrEqualTo(root.get(CriteriaConstants.COURSE),courseTo)
                )
                .build();

        return cb.and(predicates);
    }
}

