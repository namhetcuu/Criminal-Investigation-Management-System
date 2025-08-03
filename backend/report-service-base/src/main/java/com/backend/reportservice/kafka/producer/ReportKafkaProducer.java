package com.backend.reportservice.kafka.producer;

import com.backend.reportservice.dto.response.ReportDto;

public interface ReportKafkaProducer {
  void sendReportCreated(String reportId, ReportDto reportDto);
  void sendReportAccepted(String reportId, ReportDto reportDto);
  void sendReportDeleted(ReportDto reportDto);
}