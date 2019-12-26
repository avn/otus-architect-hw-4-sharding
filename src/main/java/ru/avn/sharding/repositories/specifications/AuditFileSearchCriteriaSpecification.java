package ru.avn.sharding.repositories.specifications;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.avn.sharding.beans.AuditFileSearchCriteria;
import ru.avn.sharding.repositories.entities.AuditFile;
import ru.avn.sharding.repositories.entities.AuditFile_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class AuditFileSearchCriteriaSpecification implements ExtSpecification<AuditFile> {

    private final AuditFileSearchCriteria searchCriteria;

    @SuppressWarnings("ConstantConditions")
    @Override
    public Predicate toPredicate(Root<AuditFile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return Specification.where(in(AuditFile_.id, searchCriteria.getId()))
                .and(in(AuditFile_.auditCode, searchCriteria.getAuditCode()))
                .and(in(AuditFile_.contentId, searchCriteria.getContentId()))
                .and(in(AuditFile_.filename, searchCriteria.getFilename()))
                .and(in(AuditFile_.auditQuestions, searchCriteria.getAuditQuestions()))
                .and(in(AuditFile_.type, searchCriteria.getType()))
                .toPredicate(root, query, cb);
    }
}
