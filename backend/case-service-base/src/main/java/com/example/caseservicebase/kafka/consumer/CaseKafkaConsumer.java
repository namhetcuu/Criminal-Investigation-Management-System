package com.example.caseservicebase.kafka.consumer;


import com.example.caseservicebase.dto.shared.ReportDto;

public interface CaseKafkaConsumer {
  void handleReportCreated(ReportDto reportDto);
}
