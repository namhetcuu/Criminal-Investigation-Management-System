package com.Evidence_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceDTO {
    @NotBlank(message = "Evidence ID cannot be empty")
    @Size(max = 50, message = "Evidence ID must not exceed 50 characters")
    private String evidenceId;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Detailed description cannot be empty")
    @Size(max = 1000, message = "Detailed description must not exceed 1000 characters")
    private String detailedDescription;

    @NotBlank(message = "Attached file cannot be empty")
    @Size(max = 255, message = "Attached file path must not exceed 255 characters")
    private String attachedFile;

    @NotBlank(message = "Initial condition cannot be empty")
    @Size(max = 500, message = "Initial condition must not exceed 500 characters")
    private String initialCondition;

    @NotBlank(message = "Preservation measures cannot be empty")
    @Size(max = 500, message = "Preservation measures must not exceed 500 characters")
    private String preservationMeasures;

    @NotBlank(message = "Location at scene cannot be empty")
    @Size(max = 255, message = "Location at scene must not exceed 255 characters")
    private String locationAtScene;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp cannot be null")
    private LocalDateTime updatedAt;

    @NotBlank(message = "Measure survey ID cannot be empty")
    @Size(max = 50, message = "Measure survey ID must not exceed 50 characters")
    private String measureSurveyId;

    @NotBlank(message = "Investigation plan ID cannot be empty")
    @Size(max = 50, message = "Investigation plan ID must not exceed 50 characters")
    private String investigationPlanId;

    @NotBlank(message = "Report ID cannot be empty")
    @Size(max = 50, message = "Report ID must not exceed 50 characters")
    private String reportId;

    @NotBlank(message = "Collector username cannot be empty")
    @Size(max = 50, message = "Collector username must not exceed 50 characters")
    private String collectorUsername;

    private boolean isDeleted;
}