package ru.avn.sharding.repositories.entities;

import lombok.Data;
import ru.avn.sharding.beans.AuditFileType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = AuditFile.TABLE_NAME)
public class AuditFile {

    static final String TABLE_NAME = "audit_file";

    @Id
    private UUID id;

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "audit_code")
    private String auditCode;

    @Column(name = "filename")
    private String filename;

    @Column(name = "audit_file_type")
    @Enumerated(EnumType.STRING)
    private AuditFileType type;

    @Column(name = "is_attachment")
    private boolean isAttachment;

    @ElementCollection
    @CollectionTable(name = "audit_file_question", joinColumns = @JoinColumn(name = "audit_file_id"))
    private List<String> auditQuestions;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;
}
