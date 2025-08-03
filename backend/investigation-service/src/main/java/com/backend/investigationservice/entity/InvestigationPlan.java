package com.backend.investigationservice.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "investigation_plans")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "investigation_plan_id", nullable = false)
    private UUID investigationPlanId;

    //name of the investigation plan
    @Column(name = "summary", length = 1000)
    private String summary;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "plan_content", length = 3000)
    private String planContent;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "holiday_conflict", length = 255)
    private String holidayConflict;

    @Column(name = "created_officer_name", length = 255)
    private String createdOfficerName;

    @Column(name = "accepted_officer_name", length = 255)
    private String acceptedOfficerName;

    @Column(name = "case_id")
    private UUID caseId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "interview_id")
    private UUID interviewId;

}
