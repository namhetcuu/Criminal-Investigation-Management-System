package com.example.caseservicebase.kafka.consumer.impl;

import com.example.caseservicebase.dto.shared.ReportDto;
import com.example.caseservicebase.kafka.consumer.CaseKafkaConsumer;
import com.example.caseservicebase.model.Case;
import com.example.caseservicebase.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class CaseKafkaConsumerImpl implements CaseKafkaConsumer {

  private final CaseService caseService;

  @Override
  @KafkaListener(topics = "${kafka.topic.report-accepted}", groupId = "case-service-group")
  public void handleReportCreated(ReportDto reportDto) {
    caseService.createCaseFromReport(reportDto);
  }
}
