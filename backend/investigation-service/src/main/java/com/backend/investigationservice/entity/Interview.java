package com.backend.investigationservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "interviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "interview_id", nullable = false)
    private UUID interviewId;

    @Column(name = "case_id")
    private UUID caseId;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "attached_file", length = 500)
    private String attachedFile;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "holiday_conflict", length = 255)
    private String holidayConflict;

    @Column(name = "holiday_id", length = 50)
    private String holidayId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "interviewee_type", length = 100)
    private String intervieweeType;

    @Column(name = "interviewee_id", length = 100)
    private String intervieweeId;

    @Column(name = "interviewee_name", length = 255)
    private String intervieweeName;

    @ElementCollection
    @CollectionTable(name = "interview_files", joinColumns = @JoinColumn(name = "interview_id"))
    @Column(name = "file_path")
    private List<String> attachedFiles;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    private List<WitnessInterview> witnessInterviews;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    private List<VictimInterview> victimInterviews;
}
