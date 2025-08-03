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
public class Timeline {
    @Id
    @Column(name = "timeline_id")
    private Long timelineId;

    @Column(name = "holiday_id")
    private Long holidayId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "attached_file")
    private String attachedFile;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "case_result_id")
    private CaseResult caseResultId;

    @Column(name = "activity")
    private String activity;

    @Column(name = "holiday_conflict")
    private String holidayConflict;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
