package com.example.criminalinvestigation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReportRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Reporter ID is required")
    private Long reporterId;

    // Constructors
    public ReportRequest() {}
    public ReportRequest(String title, String description, Long reporterId) {
        this.title = title;
        this.description = description;
        this.reporterId = reporterId;
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

    public Long getReporterId() {
        return reporterId;
    }
    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }
}
