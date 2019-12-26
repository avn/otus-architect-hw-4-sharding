package ru.avn.sharding.beans;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class AuditFileCreate {

    @NotNull
    private UUID id;

    @NotBlank
    private String contentId;

    @NotBlank
    private String auditCode;

    @NotBlank
    private String filename;

    private AuditFileType type;

    private boolean isAttachment;

    @NotEmpty
    private List<String> auditQuestions;
}
