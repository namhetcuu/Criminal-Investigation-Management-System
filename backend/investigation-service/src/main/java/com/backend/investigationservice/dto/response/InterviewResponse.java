package com.backend.investigationservice.dto.response;

import com.backend.investigationservice.dto.request.QuestionCreationRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewResponse {
    @JsonProperty("interview_id")
    private UUID interviewId;

    @JsonProperty("case_id")
    private UUID caseId;

    @JsonProperty("location")
    private String location;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("holiday_conflict")
    private String holidayConflict;

    @JsonProperty("holiday_id")
    private String holidayId;

    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("interviewee_type")
    private String intervieweeType;

    @JsonProperty("interviewee_id")
    private String intervieweeId;

    @JsonProperty("interviewee_name")
    private String intervieweeName;

    @JsonProperty("attached_files")
    private List<String> attachedFiles;

    @JsonProperty("questions")
    private List<QuestionResponse> questions;
}