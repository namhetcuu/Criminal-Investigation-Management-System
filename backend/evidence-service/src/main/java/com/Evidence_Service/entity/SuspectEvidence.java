package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseClass;
import com.Evidence_Service.entity.id.SuspectEvidenceId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "suspect_evidence")
@IdClass(SuspectEvidenceId.class)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SuspectEvidence extends BaseClass {
    @Id
    @Column(name = "suspect_id")
    private String suspectId;

    @Id
    @Column(name = "evidence_id")
    private String evidenceId;

    private LocalDateTime detachedAt;
    private String attachedBy;
}
