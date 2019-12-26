package ru.avn.sharding.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.avn.sharding.ProfileName;
import ru.avn.sharding.beans.AuditFileCreate;
import ru.avn.sharding.beans.AuditFileSearchCriteria;
import ru.avn.sharding.repositories.entities.AuditFile;
import ru.avn.sharding.shard.auditfile.client.AuditFileClient;

import java.util.List;


@Slf4j
@Profile(ProfileName.SHARD_PROXY)
@Service(ShardingProxyAuditFileService.SERVICE_NAME)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ShardingProxyAuditFileService implements AuditFileService {

    public static final String SERVICE_NAME = "ShardingProxyAuditFileService";

    private final AuditFileClient auditFileClient;

    @Override
    public AuditFile save(AuditFileCreate auditFileCreate) {
        return auditFileClient.save(auditFileCreate);
    }

    @Override
    public List<AuditFile> search(AuditFileSearchCriteria auditFileSearchCriteria) {
        return auditFileClient.search(auditFileSearchCriteria);
    }
}
