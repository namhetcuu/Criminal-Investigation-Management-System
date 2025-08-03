package com.Evidence_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CaseDTO {
    @NotBlank(message = "Case ID cannot be empty")
    @Size(max = 50, message = "Case ID must not exceed 50 characters")
    private String caseId;

    @NotBlank(message = "Case number cannot be empty")
    @Size(max = 50, message = "Case number must not exceed 50 characters")
    private String caseNumber;

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status;

    @NotBlank(message = "Summary cannot be empty")
    @Size(max = 500, message = "Summary must not exceed 500 characters")
    private String summary;

    @NotBlank(message = "Severity cannot be empty")
    @Size(max = 20, message = "Severity must not exceed 20 characters")
    private String severity;

    @NotNull(message = "Created at timestamp cannot be null")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated at timestamp cannot be null")
    private LocalDateTime updatedAt;
}