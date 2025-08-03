package com.Evidence_Service.entity;

import com.Evidence_Service.entity.base.BaseClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "evidence")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Evidence extends BaseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "evidence_id")
    private String evidenceId;

    @Column(name = "case_id")
    private String caseId;

    @Column(name = "report_id")
    private String reportId;

    @Column(name = "warrantResult_id")
    private String warrantResultId;

    @Column(name = "measure_survey_id")
    private String measureSurveyId;

    @Column(name = "investigation_plan_id")
    private String investigationPlanId;

    private String description;
    private String collectorUsername;
    private String detailedDescription;
    private String initialCondition;
    private String preservationMeasures;
    private String locationAtScene;
    private String currentLocation;
    private String attachedFile;
    private EvidenceStatus status;
}
