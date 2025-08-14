package com.example.criminalinvestigation.dto.response;

import java.time.LocalDateTime;

public class ReportResponse {
    private Long id;
    private String title;
    private String description;
    private String reporterName;
    private LocalDateTime createdAt;

    // Constructors
    public ReportResponse() {}
    public ReportResponse(Long id, String title, String description, String reporterName, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporterName = reporterName;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public String getReporterName() {
        return reporterName;
    }
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
