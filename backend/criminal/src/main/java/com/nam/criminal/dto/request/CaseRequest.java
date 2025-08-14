package com.example.criminalinvestigation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CaseRequest {
    @NotBlank(message = "Case title is required")
    private String title;

    @NotBlank(message = "Case description is required")
    private String description;

    @NotNull(message = "Report ID is required")
    private Long reportId;

    // Constructors
    public CaseRequest() {}
    public CaseRequest(String title, String description, Long reportId) {
        this.title = title;
        this.description = description;
        this.reportId = reportId;
    }

    // Getters & Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getReportId() {
        return reportId;
    }
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
}
