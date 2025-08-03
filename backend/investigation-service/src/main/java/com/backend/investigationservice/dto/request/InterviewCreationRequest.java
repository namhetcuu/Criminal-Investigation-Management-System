package com.backend.investigationservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewCreationRequest {

    @JsonProperty("case_id")
    @NotNull(message = "Case ID must not be null")
    private UUID caseId;

    @JsonProperty("location")
    @NotBlank(message = "Location must not be blank")
    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;

    @JsonProperty("start_time")
    @NotNull(message = "Start time must not be null")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    @NotNull(message = "End time must not be null")
    private LocalDateTime endTime;

    @JsonProperty("holiday_conflict")
    @Size(max = 255, message = "Holiday conflict must not exceed 255 characters")
    private String holidayConflict;

    @JsonProperty("holiday_id")
    @Size(max = 50, message = "Holiday ID must not exceed 50 characters")
    private String holidayId;

    @JsonProperty("interviewee_type")
    @NotBlank(message = "Interviewee type must not be blank")
    @Size(max = 100, message = "Interviewee type must not exceed 100 characters")
    private String intervieweeType;

    @JsonProperty("interviewee_id")
    @NotBlank(message = "Interviewee ID must not be blank")
    @Size(max = 100, message = "Interviewee ID must not exceed 100 characters")
    private String intervieweeId;

    @JsonProperty("interviewee_name")
    @NotBlank(message = "Interviewee name must not be blank")
    @Size(max = 255, message = "Interviewee name must not exceed 255 characters")
    private String intervieweeName;

    @JsonProperty("attached_files")
    @Size(max = 20, message = "You can attach up to 20 files")
    private List<@Size(max = 500, message = "Each file path must not exceed 500 characters") String> attachedFiles;

    @JsonProperty("questions")
    @Size(max = 50, message = "You can add up to 50 questions")
    private List<@Valid QuestionCreationRequest> questions;
}
