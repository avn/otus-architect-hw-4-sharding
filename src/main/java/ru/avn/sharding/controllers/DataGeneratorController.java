package ru.avn.sharding.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avn.sharding.ProfileName;
import ru.avn.sharding.beans.AuditFileCreate;
import ru.avn.sharding.beans.AuditFileType;
import ru.avn.sharding.services.AuditFileService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Validated
@RestController
@RequestMapping("/generator")
@Profile(ProfileName.SHARD_PROXY)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DataGeneratorController {

    private final AuditFileService auditFileService;

    private final AtomicInteger sequence = new AtomicInteger(0);


    @GetMapping("/{size}")
    public ResponseEntity<Void> generate(@PathVariable @Max(1000) @Positive int size) {
        if (sequence.get() > 10000) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        IntStream.range(0, size)
                .forEach(value -> {
                    int seqVal = sequence.getAndIncrement();
                    AuditFileCreate auditFileCreate = new AuditFileCreate();
                    auditFileCreate.setAuditCode(String.format("%04d", seqVal));
                    auditFileCreate.setAuditQuestions(Collections.singletonList("01"));
                    auditFileCreate.setContentId(UUID.randomUUID()
                            .toString());
                    auditFileCreate.setFilename(String.format("file-%05d.doc", value));
                    auditFileCreate.setType(AuditFileType.values()[seqVal % AuditFileType.values().length]);
                    auditFileCreate.setId(UUID.randomUUID());
                    auditFileService.save(auditFileCreate);
                });

        return ResponseEntity.ok()
                .build();
    }
}
