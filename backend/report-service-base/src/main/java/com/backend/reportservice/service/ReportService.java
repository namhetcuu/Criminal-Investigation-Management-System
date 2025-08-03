package com.backend.reportservice.service;

import com.backend.reportservice.dto.response.ReportDto;
import com.backend.reportservice.dto.request.ReportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    Page<ReportDto> getAllReports(Pageable pageable);
    Page<ReportDto> getReports(String status, String severity, String typeOfReport, LocalDateTime reportAt, Pageable pageable);
    ReportDto getReportById(String id);
    ReportDto createReport(ReportRequest reportRequest);
    ReportDto acceptReport(String id);
    ReportDto rejectReport(String id);

    void deleteReport(String id);
    List<String> getAllStatuses();
    List<String> getAllSeverities();
    List<String> getAllReportTypes();
}