package ru.avn.sharding.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.avn.sharding.ProfileName;
import ru.avn.sharding.beans.AuditFileCreate;
import ru.avn.sharding.beans.AuditFileSearchCriteria;
import ru.avn.sharding.repositories.AuditFileRepository;
import ru.avn.sharding.repositories.entities.AuditFile;
import ru.avn.sharding.repositories.specifications.AuditFileSearchCriteriaSpecification;

import java.util.Date;
import java.util.List;

@Slf4j
@Profile(ProfileName.SHARD_REPOSITORY)
@Service(RepositoryAuditFileService.SERVICE_NAME)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RepositoryAuditFileService implements AuditFileService {

    public static final String SERVICE_NAME = "RepositoryAuditFileService";

    private final AuditFileRepository auditFileRepository;

    @Override
    public AuditFile save(AuditFileCreate auditFileCreate) {
        log.debug("Starting saving audit file '{}'", auditFileCreate);

        AuditFile auditFile = new AuditFile();
        auditFile.setId(auditFileCreate.getId());
        auditFile.setAttachment(auditFileCreate.isAttachment());
        auditFile.setAuditCode(auditFileCreate.getAuditCode());
        auditFile.setAuditQuestions(auditFileCreate.getAuditQuestions());
        auditFile.setContentId(auditFileCreate.getContentId());
        auditFile.setFilename(auditFileCreate.getFilename());
        auditFile.setType(auditFileCreate.getType());
        auditFile.setCreatedAt(new Date());

        auditFileRepository.save(auditFile);

        log.debug("Audit file entity is put to persistent layer. {}", auditFileCreate);

        return auditFile;
    }

    @Override
    public List<AuditFile> search(AuditFileSearchCriteria auditFileSearchCriteria) {
        log.debug("Starting search audit files by criteria '{}'", auditFileSearchCriteria);
        List<AuditFile> result = auditFileRepository.findAll(
                new AuditFileSearchCriteriaSpecification(auditFileSearchCriteria));
        log.debug("Finished search audit files by criteria '{}'. Found '{}' files", auditFileSearchCriteria,
                result.size());
        return result;
    }
}
