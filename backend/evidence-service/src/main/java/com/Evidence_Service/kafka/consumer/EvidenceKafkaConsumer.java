package com.Evidence_Service.kafka.consumer;

import com.Evidence_Service.event.listener.*;
import com.Evidence_Service.kafka.handler.EvidenceServiceEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EvidenceKafkaConsumer {

    private final EvidenceServiceEventHandler handler;

    @KafkaListener(topics = "case.created", groupId = "evidence-service-case-created", containerFactory = "caseCreatedKafkaListenerFactory")
    public void handleCaseCreated(CaseCreatedEvent event) {
        log.info("Received case.created: {}", event);
        handler.onCaseCreated(event);
    }

    @KafkaListener(topics = "case.deleted", groupId = "evidence-service-case-deleted", containerFactory = "caseDeletedKafkaListenerFactory")
    public void handleCaseDeleted(CaseDeletedEvent event) {
        log.info("Received case.deleted: {}", event);
        handler.onCaseDeleted(event);
    }

    @KafkaListener(topics = "report.created", groupId = "evidence-service-report-created", containerFactory = "reportCreatedKafkaListenerFactory")
    public void handleReportCreated(ReportCreatedEvent event) {
        log.info("Received report.created: {}", event);
        handler.onReportCreated(event);
    }

    @KafkaListener(topics = "report.deleted", groupId = "evidence-service-report-deleted", containerFactory = "reportDeletedKafkaListenerFactory")
    public void handleReportDeleted(ReportDeletedEvent event) {
        log.info("Received report.deleted: {}", event);
        handler.onReportDeleted(event);
    }

    @KafkaListener(topics = "suspect.deleted", groupId = "evidence-service-suspect-deleted", containerFactory = "suspectDeletedKafkaListenerFactory")
    public void handleSuspectDeleted(SuspectDeletedEvent event) {
        log.info("Received suspect.deleted: {}", event);
        handler.onSuspectDeleted(event);
    }

    @KafkaListener(topics = "evidence.analysis-recorded", groupId = "evidence-service-analysis", containerFactory = "analysisResultKafkaListenerFactory")
    public void handleAnalysisResult(AnalysisResultEvent event) {
        log.info("Received evidence.analysis-recorded: {}", event);
        handler.onAnalysisResultRecorded(event);
    }

    @KafkaListener(topics = "evidence.suspect-assigned", groupId = "evidence-service-suspect-assigned", containerFactory = "suspectAssignedKafkaListenerFactory")
    public void handleSuspectAssigned(SuspectAssignedEvent event) {
        log.info("Received evidence.suspect-assigned: {}", event);
        handler.onSuspectAssigned(event);
    }

    @KafkaListener(topics = "evidence.case-assigned", groupId = "evidence-service-case-assigned", containerFactory = "caseAssignedKafkaListenerFactory")
    public void handleCaseAssigned(CaseAssignedEvent event) {
        log.info("Received evidence.case-assigned: {}", event);
        handler.onCaseAssigned(event);
    }

    @KafkaListener(topics = "warrant.assigned", groupId = "evidence-service-warrant-assigned", containerFactory = "warrantAssignedKafkaListenerFactory")
    public void handleWarrantCreated(WarrantAssignedEvent event) {
        log.info("Received warrant.created: {}", event);
        handler.onWarrantCreated(event);
    }

    @KafkaListener(topics = "warrant.deleted", groupId = "evidence-service-warrant-deleted", containerFactory = "warrantDeletedKafkaListenerFactory")
    public void handleWarrantDeleted(WarrantDeletedEvent event) {
        log.info("Received warrant.deleted: {}", event);
        handler.onWarrantDeleted(event);
    }

    @KafkaListener(topics = "investigation.assigned", groupId = "evidence-service-investigation-assigned", containerFactory = "investigationAssignedKafkaListenerFactory")
    public void handleInvestigationCreated(ResultInvestAssignedEvent event) {
        log.info("Received investigation.assigned: {}", event);
        handler.onInvestigationAssigned(event);
    }

    @KafkaListener(topics = "investigation.deleted", groupId = "evidence-service-investigation-deleted", containerFactory = "investigationDeletedKafkaListenerFactory")
    public void handleInvestigationDeleted(DeleteInvestResultEvent event) {
        log.info("Received investigation.deleted: {}", event);
        handler.onInvestigationDeleted(event);
    }
}
