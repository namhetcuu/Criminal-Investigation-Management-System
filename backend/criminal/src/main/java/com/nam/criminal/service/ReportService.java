// ReportService.java
package com.example.backend.service;

import com.example.backend.dto.request.ReportRequest;
import com.example.backend.dto.response.ReportResponse;
import java.util.List;

public interface ReportService {
    ReportResponse createReport(ReportRequest request);
    List<ReportResponse> getAllReports();
}
