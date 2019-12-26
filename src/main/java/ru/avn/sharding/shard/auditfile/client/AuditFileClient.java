package ru.avn.sharding.shard.auditfile.client;

import ru.avn.sharding.beans.AuditFileCreate;
import ru.avn.sharding.beans.AuditFileSearchCriteria;
import ru.avn.sharding.repositories.entities.AuditFile;

import java.util.List;

public interface AuditFileClient {

    List<AuditFile> search(AuditFileSearchCriteria searchCriteria);

    AuditFile save(AuditFileCreate auditFileCreate);

}
