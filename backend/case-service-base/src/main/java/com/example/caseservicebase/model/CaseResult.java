package com.example.caseservicebase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseResult {
    @Id
    @Column(name = "case_result_id")
    private Long caseResultId;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case caseId;

    @Column(name = "report_time")
    private LocalDateTime reportTime;

    @Column(name = "report_analyst")
    private String reportAnalyst;

    @Column(name = "summary")
    private String summary;

    @Column(name = "identify_motive")
    private String identifyMotive;

    @Column(name = "status")
    private String status;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}