package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseClass;
import com.Evidence_Service.entity.id.ReportEvidenceId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Table(name = "report_evidence")
@IdClass(ReportEvidenceId.class)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ReportEvidence extends BaseClass {
    @Id
    @Column(name = "report_id")
    private String reportId;

    @Id
    @Column(name = "evidence_id")
    private String evidenceId;

    private String attachedBy;
}
