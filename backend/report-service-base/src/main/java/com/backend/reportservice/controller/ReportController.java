package com.backend.reportservice.controller;

import com.backend.reportservice.dto.response.ApiResponse;
import com.backend.reportservice.dto.response.ReportDto;
import com.backend.reportservice.dto.request.ReportRequest;
import com.backend.reportservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<Page<ReportDto>> getAllReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReportDto> reports = reportService.getAllReports(pageable);
        return ApiResponse.<Page<ReportDto>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved all reports")
                .data(reports)
                .build();
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<Page<ReportDto>> getReports(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String typeOfReport,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reportAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReportDto> reports = reportService.getReports(status, severity, typeOfReport, reportAt, pageable);
        return ApiResponse.<Page<ReportDto>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved filtered reports")
                .data(reports)
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<ReportDto> getReportById(@PathVariable String id) {
        ReportDto report = reportService.getReportById(id);
        return ApiResponse.<ReportDto>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved report")
                .data(report)
                .build();
    }

    @GetMapping("/statuses")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<List<String>> getAllStatuses() {
        return ApiResponse.<List<String>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved all statuses")
                .data(reportService.getAllStatuses())
                .build();
    }

    @GetMapping("/severities")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<List<String>> getAllSeverities() {
        return ApiResponse.<List<String>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved all severities")
                .data(reportService.getAllSeverities())
                .build();
    }

    @GetMapping("/report-types")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<List<String>> getAllReportTypes() {
        return ApiResponse.<List<String>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved all report types")
                .data(reportService.getAllReportTypes())
                .build();
    }

    @PostMapping
    public ApiResponse<ReportDto> createReport(@Valid @RequestBody ReportRequest reportRequest) {
        ReportDto savedReport = reportService.createReport(reportRequest);
        return ApiResponse.<ReportDto>builder()
                .code(HttpStatus.CREATED.value())
                .message("Report created successfully")
                .data(savedReport)
                .build();
    }

    @PutMapping("/accept/{id}")
    @PreAuthorize("hasAuthority('REPORT_ACCEPT')")
    public ApiResponse<ReportDto> acceptReport(@PathVariable String id) {
        ReportDto acceptedReport = reportService.acceptReport(id);
        return ApiResponse.<ReportDto>builder()
                .code(HttpStatus.OK.value())
                .message("Report accepted successfully")
                .data(acceptedReport)
                .build();
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasAuthority('REPORT_REJECT')")
    public ApiResponse<ReportDto> rejectReport(@PathVariable String id) {
        ReportDto rejectedReport = reportService.rejectReport(id);
        return ApiResponse.<ReportDto>builder()
                .code(HttpStatus.OK.value())
                .message("Report accepted successfully")
                .data(rejectedReport)
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REPORT_DELETE')")
    public ApiResponse<Void> deleteReport(@PathVariable String id) {
        reportService.deleteReport(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Report deleted successfully")
                .build();
    }
}