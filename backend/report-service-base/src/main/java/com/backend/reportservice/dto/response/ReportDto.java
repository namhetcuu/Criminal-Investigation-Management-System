package com.backend.reportservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDto {
    @NotBlank
    private String reportId;

    @NotBlank
    private String caseId;

    @NotBlank
    private String typeReport;

    @NotBlank
    private String severity;

    @NotBlank
    private String description;

    @NotBlank
    private String caseLocation;

    @NotNull
    private LocalDateTime reportedAt;

    @NotBlank
    private String reporterFullname;

    @NotBlank
    private String reporterEmail;

    @NotBlank
    private String reporterPhoneNumber;


    @NotBlank
    private String status;

    @NotBlank
    private String officerApproveUsername;

    private Boolean isDeleted = false;
}