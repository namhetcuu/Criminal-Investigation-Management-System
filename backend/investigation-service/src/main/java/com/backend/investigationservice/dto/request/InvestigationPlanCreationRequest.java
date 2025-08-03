package com.backend.investigationservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvestigationPlanCreationRequest {

    @JsonProperty("summary")
    @NotBlank(message = "Summary must not be blank")
    @Size(max = 1000, message = "Summary must not exceed 1000 characters")
    String summary;

    @JsonProperty("create_at")
    @NotNull(message = "Creation date must not be null")
    LocalDateTime createAt;

    @JsonProperty("deadline_date")
    @NotNull(message = "Deadline date must not be null")
    LocalDateTime deadlineDate;

    @JsonProperty("status")
    @NotBlank(message = "Status must not be blank")
    @Size(max = 50, message = "Status must not exceed 50 characters")
    String status;

    @JsonProperty("plan_content")
    @Size(max = 3000, message = "Plan content must not exceed 3000 characters")
    String planContent;

    @JsonProperty("type")
    @Size(max = 100, message = "Type must not exceed 100 characters")
    String type;

    @JsonProperty("holiday_conflict")
    @Size(max = 255, message = "Holiday conflict must not exceed 255 characters")
    String holidayConflict;

    @JsonProperty("created_officer_name")
    @Size(max = 255, message = "Created officer name must not exceed 255 characters")
    String createdOfficerName;

    @JsonProperty("accepted_officer_name")
    @Size(max = 255, message = "Accepted officer name must not exceed 255 characters")
    String acceptedOfficerName;

    @JsonProperty("case_id")
    @NotBlank(message = "Case ID must not be blank")
    String caseId;
}
