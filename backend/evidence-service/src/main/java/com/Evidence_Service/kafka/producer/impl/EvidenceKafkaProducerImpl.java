package com.Evidence_Service.kafka.producer.impl;

import com.Evidence_Service.event.caller.EvidenceCreatedEvent;
import com.Evidence_Service.event.caller.EvidenceDeletedEvent;
import com.Evidence_Service.event.listener.AnalysisResultEvent;
import com.Evidence_Service.event.listener.CaseAssignedEvent;
import com.Evidence_Service.event.listener.SuspectAssignedEvent;
import com.Evidence_Service.event.listener.WarrantAssignedEvent;
import com.Evidence_Service.kafka.producer.EvidenceKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvidenceKafkaProducerImpl implements EvidenceKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendEvidenceCreated(EvidenceCreatedEvent event) {
        kafkaTemplate.send("evidence.created", event);
        log.info("Sent evidence.created: {}", event);
    }

    @Override
    public void sendAnalysisResultRecorded(AnalysisResultEvent event) {
        kafkaTemplate.send("evidence.analysis-recorded", event);
        log.info("Sent evidence.analysis-recorded: {}", event);
    }

    @Override
    public void sendSuspectAssigned(SuspectAssignedEvent event) {
        kafkaTemplate.send("evidence.suspect-assigned", event);
        log.info("Sent evidence.suspect-assigned: {}", event);
    }

    @Override
    public void sendCaseAssigned(CaseAssignedEvent event) {
        kafkaTemplate.send("evidence.case-assigned", event);
        log.info("Sent evidence.case-assigned: {}", event);
    }

    @Override
    public void sendEvidenceDeleted(String evidenceId) {
        kafkaTemplate.send("evidence.deleted", new EvidenceDeletedEvent());
        log.info("Sent evidence.deleted: {}", evidenceId);
    }

    @Override
    public void sendWarrantAssigned(WarrantAssignedEvent event) {
        kafkaTemplate.send("warrant.case-assigned", new WarrantAssignedEvent());
        log.info("Sent warrant.case-assigned: {}", event);
    }
}

