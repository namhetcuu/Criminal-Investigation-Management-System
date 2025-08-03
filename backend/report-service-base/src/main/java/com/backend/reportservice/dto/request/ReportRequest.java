package com.backend.reportservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReportRequest {
  @NotBlank
  private String typeReport;

  @NotBlank
  private String description;

  @NotBlank
  private String caseLocation;


  @NotBlank
  private String reporterFullname;

  @NotBlank
  private String reporterEmail;

  @NotBlank
  private String reporterPhoneNumber;
}