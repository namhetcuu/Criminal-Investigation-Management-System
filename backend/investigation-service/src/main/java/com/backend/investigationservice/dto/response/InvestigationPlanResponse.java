package com.backend.investigationservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvestigationPlanResponse {

    @JsonProperty("investigation_plan_id")
    UUID investigationPlanId;

    @JsonProperty("summary")
    String summary;

    @JsonProperty("create_at")
    LocalDateTime createAt;

    @JsonProperty("deadline_date")
    LocalDateTime deadlineDate;

    @JsonProperty("status")
    String status;

    @JsonProperty("plan_content")
    String planContent;

    @JsonProperty("type")
    String type;

    @JsonProperty("holiday_conflict")
    String holidayConflict;

    @JsonProperty("created_officer_name")
    String createdOfficerName;

    @JsonProperty("accepted_officer_name")
    String acceptedOfficerName;

    @JsonProperty("case_id")
    UUID caseId;
}