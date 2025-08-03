package com.example.caseservicebase.dto.shared;

import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
  private Long id;
  private Long caseId;
  private String typeReport;
  private String severity;

  private String description;
  private String caseLocation;
  private LocalDateTime reportedAt;
  private String reporterFullname;
  private String reporterEmail;
  private String reporterPhoneNumber;

  private String status;
  private String officerApproveUsername;
  private Boolean isDeleted = false;
}
