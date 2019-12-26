package ru.avn.sharding.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.avn.sharding.repositories.entities.AuditFile;

import java.util.UUID;

public interface AuditFileRepository extends JpaRepository<AuditFile, UUID>, JpaSpecificationExecutor<AuditFile> {

}
