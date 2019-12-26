package ru.avn.sharding.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.avn.sharding.beans.AuditFileCreate;
import ru.avn.sharding.beans.AuditFileSearchCriteria;
import ru.avn.sharding.repositories.entities.AuditFile;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface AuditFileService {

    @Transactional(propagation = Propagation.REQUIRED)
    AuditFile save(@Valid AuditFileCreate auditFileCreate);

    @Transactional(propagation = Propagation.REQUIRED)
    List<AuditFile> search(@Valid AuditFileSearchCriteria auditFileSearchCriteria);

}
