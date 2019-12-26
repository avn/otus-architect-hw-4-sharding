package ru.avn.sharding.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avn.sharding.beans.AuditFileCreate;
import ru.avn.sharding.beans.AuditFileSearchCriteria;
import ru.avn.sharding.repositories.entities.AuditFile;
import ru.avn.sharding.services.AuditFileService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/audit_files")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuditFileController {

    private final AuditFileService auditFileService;

    @GetMapping
    ResponseEntity<List<AuditFile>> get(@PathVariable UUID fileId) {

        List<AuditFile> searchResult = auditFileService.search(AuditFileSearchCriteria.byId(fileId));
        if (searchResult.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping("/search")
    List<AuditFile> search(AuditFileSearchCriteria auditFileSearchCriteria) {
        log.debug("Starting search by criteria '{}'", auditFileSearchCriteria);

        return auditFileService.search(auditFileSearchCriteria);
    }

    @PostMapping
    AuditFile save(@RequestBody AuditFileCreate auditFileCreate) {
        return auditFileService.save(auditFileCreate);
    }

}
