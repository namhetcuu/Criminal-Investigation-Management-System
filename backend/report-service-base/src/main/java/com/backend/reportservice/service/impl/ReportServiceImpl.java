package com.backend.reportservice.service.impl;

import com.backend.reportservice.dto.response.ReportDto;
import com.backend.reportservice.dto.request.ReportRequest;
import com.backend.reportservice.entity.Report;
import com.backend.reportservice.enums.Status;
import com.backend.reportservice.exception.AppException;
import com.backend.reportservice.exception.ErrorCode;
import com.backend.reportservice.kafka.producer.ReportKafkaProducer;
import com.backend.reportservice.mapper.ReportMapper;
import com.backend.reportservice.repository.ReportRepository;
import com.backend.reportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportKafkaProducer reportKafkaProducer;
    private final ReportMapper reportMapper;
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    @Cacheable(value = "reports", key = "'allReports_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<ReportDto> getAllReports(Pageable pageable) {
        try {
            // Fetch all reports with pagination from the repository
            logger.info("Fetching all reports for page {} with size {}", pageable.getPageNumber(), pageable.getPageSize());
            Page<Report> reports = reportRepository.findAll(pageable);
            Page<ReportDto> result = reports.map(reportMapper::toDto);
            logger.info("Successfully retrieved {} reports", result.getTotalElements());
            return result;
        } catch (Exception e) {
            // Log error if fetching reports fails
            logger.error("Error fetching all reports for page {}: {}", pageable.getPageNumber(), e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "reports", key = "'filteredReports_' + #status + '_' + #severity + '_' + #typeOfReport + '_' + #reportAt + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<ReportDto> getReports(String status, String severity, String typeOfReport, LocalDateTime reportAt, Pageable pageable) {
        try {
            // Fetch reports with pagination and apply filtering
            logger.info("Fetching filtered reports with status: {}, severity: {}, type: {}, reportAt: {}, page: {}, size: {}",
                    status, severity, typeOfReport, reportAt, pageable.getPageNumber(), pageable.getPageSize());
            Page<Report> reports = reportRepository.findAll(pageable);
            List<ReportDto> filteredReports = reports.getContent().stream()
                    .filter(report -> status == null || report.getStatus().equals(status.toString()))
                    .filter(report -> severity == null || report.getSeverity().equalsIgnoreCase(severity))
                    .filter(report -> typeOfReport == null || report.getTypeReport().equalsIgnoreCase(typeOfReport))
                    .filter(report -> reportAt == null || report.getReportedAt().isEqual(reportAt))
                    .map(reportMapper::toDto)
                    .collect(Collectors.toList());
            Page<ReportDto> result = new PageImpl<>(filteredReports, pageable, filteredReports.size());
            logger.info("Successfully retrieved {} filtered reports", filteredReports.size());
            return result;
        } catch (Exception e) {
            // Log error if filtering reports fails
            logger.error("Error filtering reports with status: {}, severity: {}, type: {}, reportAt: {}: {}",
                    status, severity, typeOfReport, reportAt, e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "reports", key = "'report_' + #id")
    public ReportDto getReportById(String id) {
        try {
            // Fetch a single report by ID
            logger.info("Fetching report with ID: {}", id);
            Report report = reportRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
            ReportDto result = reportMapper.toDto(report);
            logger.info("Successfully retrieved report with ID: {}", id);
            return result;
        } catch (AppException e) {
            // Log specific error for report not found
            logger.error("Report not found with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            // Log unexpected errors during report retrieval
            logger.error("Error fetching report with ID: {}: {}", id, e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "reports", allEntries = true)
    public ReportDto createReport(ReportRequest reportRequest) {
        try {
            // Create a new report from the request
            logger.info("Creating new report with request: {}", reportRequest);
            Report report = reportMapper.createReport(reportRequest);
            report.setReportedAt(LocalDateTime.now());
            report.setStatus(Status.PENDING);
            Report savedReport = reportRepository.save(report);
            ReportDto reportDto = reportMapper.toDto(savedReport);
            // Send Kafka event for report creation
            reportKafkaProducer.sendReportCreated(savedReport.getReportId(), reportDto);
            logger.info("Report created successfully: {}", reportDto);
            return reportDto;
        } catch (Exception e) {
            // Log error if report creation fails
            logger.error("Error creating report with request: {}: {}", reportRequest, e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "reports", allEntries = true)
    public ReportDto acceptReport(String id) {
        try {
            // Accept a report by updating its status
            logger.info("Accepting report with ID: {}", id);
            Report report = reportRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
            report.setStatus(Status.APPROVED);
            Report updatedReport = reportRepository.save(report);
            ReportDto reportDto = reportMapper.toDto(updatedReport);
            // Send Kafka event for report acceptance
            reportKafkaProducer.sendReportAccepted(id, reportDto);
            logger.info("Report APPROVED successfully: {}", reportDto);
            return reportDto;
        } catch (AppException e) {
            // Log specific error for report not found
            logger.error("Report not found with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            // Log unexpected errors during report acceptance
            logger.error("Error APPROVED report with ID: {}: {}", id, e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "reports", allEntries = true)
    public ReportDto rejectReport(String id) {
        try {
            // Accept a report by updating its status
            logger.info("Rejecting report with ID: {}", id);
            Report report = reportRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
            report.setStatus(Status.REJECTED);
            Report updatedReport = reportRepository.save(report);
            ReportDto reportDto = reportMapper.toDto(updatedReport);
            // Send Kafka event for report acceptance
            reportKafkaProducer.sendReportDeleted(ReportDto.builder()
                            .reportId(id)
                    .build());
            logger.info("Report REJECTED successfully: {}", reportDto);
            return reportDto;
        } catch (AppException e) {
            // Log specific error for report not found
            logger.error("Report not found with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            // Log unexpected errors during report acceptance
            logger.error("Error APPROVED report with ID: {}: {}", id, e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "reports", allEntries = true)
    public void deleteReport(String id) {
        try {
            // Delete a report by ID
            logger.info("Deleting report with ID: {}", id);
            Report report = reportRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
            reportRepository.delete(report);
            // Send Kafka event for report deletion
            reportKafkaProducer.sendReportDeleted(ReportDto.builder()
                            .reportId(id)
                    .build());
            logger.info("Report deleted successfully: {}", id);
        } catch (AppException e) {
            // Log specific error for report not found
            logger.error("Report not found with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            // Log unexpected errors during report deletion
            logger.error("Error deleting report with ID: {}: {}", id, e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "statuses")
    public List<String> getAllStatuses() {
        try {
            logger.info("Fetching all report statuses");
            List<String> statuses = reportRepository.findDistinctStatuses()
                    .stream()
                    .map(Status::name)
                    .collect(Collectors.toList());
            logger.info("Successfully retrieved {} statuses", statuses.size());
            return statuses;
        } catch (Exception e) {
            logger.error("Error fetching statuses: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "severities")
    public List<String> getAllSeverities() {
        try {
            // Fetch all unique severities
            logger.info("Fetching all report severities");
            List<String> severities = reportRepository.findAll()
                    .stream()
                    .map(Report::getSeverity)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            logger.info("Successfully retrieved {} severities", severities.size());
            return severities;
        } catch (Exception e) {
            // Log error if fetching severities fails
            logger.error("Error fetching severities: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "reportTypes")
    public List<String> getAllReportTypes() {
        try {
            // Fetch all unique report types
            logger.info("Fetching all report types");
            List<String> reportTypes = reportRepository.findAll()
                    .stream()
                    .map(Report::getTypeReport)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            logger.info("Successfully retrieved {} report types", reportTypes.size());
            return reportTypes;
        } catch (Exception e) {
            // Log error if fetching report types fails
            logger.error("Error fetching report types: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}