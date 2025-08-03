package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseClass;
import com.Evidence_Service.entity.id.CaseEvidenceId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CaseEvidenceId.class)
public class CaseEvidence extends BaseClass {
    @Id
    @Column(name = "case_id")
    private String caseId;

    @Id
    @Column(name = "evidence_id")
    private String evidenceId;
}
