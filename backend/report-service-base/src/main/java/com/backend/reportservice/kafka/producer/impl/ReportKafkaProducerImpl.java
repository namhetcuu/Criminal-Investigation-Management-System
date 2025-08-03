package com.backend.reportservice.kafka.producer.impl;

import com.backend.reportservice.dto.response.ReportDto;
import com.backend.reportservice.exception.AppException;
import com.backend.reportservice.exception.ErrorCode;
import com.backend.reportservice.kafka.producer.ReportKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ReportKafkaProducerImpl implements ReportKafkaProducer {

  private final KafkaTemplate<String, ReportDto> kafkaTemplate;
  private static final Logger logger = LoggerFactory.getLogger(ReportKafkaProducerImpl.class);

  private static final String REPORT_CREATED_TOPIC = "report.created";
  private static final String REPORT_ACCEPTED_TOPIC = "report.accepted";
  private static final String REPORT_DELETED_TOPIC = "report.deleted";

  public void sendReportCreated(String reportId, ReportDto reportDto) {
    try {
      logger.info("Sending report created event for ID: {} to topic: {}", reportId, REPORT_CREATED_TOPIC);
      CompletableFuture<SendResult<String, ReportDto>> future =
              kafkaTemplate.send(REPORT_CREATED_TOPIC, reportId, reportDto);

      future.whenComplete((result, ex) -> {
        if (ex == null) {
          logger.info("Successfully sent report created event for ID: {}", reportId);
        } else {
          logger.error("Failed to send report created event for ID: {}: {}", reportId, ex.getMessage(), ex);
        }
      });

    } catch (Exception e) {
      logger.error("Error sending report created event for ID: {}: {}", reportId, e.getMessage(), e);
      throw new AppException(ErrorCode.KAFKA_PUBLISH_FAILED);
    }
  }

  @Override
  public void sendReportAccepted(String reportId, ReportDto reportDto) {
    try {
      logger.info("Sending report accepted event for ID: {} to topic: {}", reportId, REPORT_ACCEPTED_TOPIC);
      CompletableFuture<SendResult<String, ReportDto>> future =
              kafkaTemplate.send(REPORT_ACCEPTED_TOPIC, reportId, reportDto);

      future.whenComplete((result, ex) -> {
        if (ex == null) {
          logger.info("Successfully sent report accepted event for ID: {}", reportId);
        } else {
          logger.error("Failed to send report accepted event for ID: {}: {}", reportId, ex.getMessage(), ex);
        }
      });

    } catch (Exception e) {
      logger.error("Error sending report accepted event for ID: {}: {}", reportId, e.getMessage(), e);
      throw new AppException(ErrorCode.KAFKA_PUBLISH_FAILED);
    }
  }

  @Override
  public void sendReportDeleted(ReportDto reportDto) {
    String reportId = reportDto.getReportId();
    try {
      logger.info("Sending report deleted event for ID: {} to topic: {}", reportId, REPORT_DELETED_TOPIC);
      CompletableFuture<SendResult<String, ReportDto>> future =
              kafkaTemplate.send(REPORT_DELETED_TOPIC, reportId, reportDto);

      future.whenComplete((result, ex) -> {
        if (ex == null) {
          logger.info("Successfully sent report deleted event for ID: {}", reportId);
        } else {
          logger.error("Failed to send report deleted event for ID: {}: {}", reportId, ex.getMessage(), ex);
        }
      });

    } catch (Exception e) {
      logger.error("Error sending report deleted event for ID: {}: {}", reportId, e.getMessage(), e);
      throw new AppException(ErrorCode.KAFKA_PUBLISH_FAILED);
    }
  }
}
