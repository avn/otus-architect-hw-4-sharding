package ru.avn.sharding.beans;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
public class AuditFileSearchCriteria {

    private List<UUID> id;

    private List<String> auditCode;

    private List<String> contentId;

    private List<String> filename;

    private List<AuditFileType> type;

    private Boolean isAttachment;

    private List<String> auditQuestions;

    public static AuditFileSearchCriteria byId(UUID uuid) {
        Assert.notNull(uuid, "uuid is required");
        AuditFileSearchCriteria searchCriteria = new AuditFileSearchCriteria();
        searchCriteria.setId(Collections.singletonList(uuid));
        return searchCriteria;
    }
}
