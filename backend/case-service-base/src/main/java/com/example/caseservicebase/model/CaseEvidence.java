package com.example.caseservicebase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseEvidence {
    @Id
    @Column(name = "evidence_id")
    private Long evidenceId;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case caseId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}