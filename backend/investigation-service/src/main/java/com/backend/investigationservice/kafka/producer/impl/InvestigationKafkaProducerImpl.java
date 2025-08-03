package com.backend.investigationservice.kafka.producer.impl;

import com.backend.investigationservice.event.listener.*;
import com.backend.investigationservice.kafka.producer.InvestigationKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestigationKafkaProducerImpl implements InvestigationKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendDigitalInvestResultCreated(DigitalInvestResultCreateEvent event) {
        kafkaTemplate.send("digital-invest-result-create", event);
        log.info("Sent digital-invest-result-create: {}", event);
    }

    @Override
    public void sendDigitalInvestResultUpdated(DigitalInvestResultUpdateEvent event) {
        kafkaTemplate.send("digital-invest-result-update", event);
        log.info("Sent digital-invest-result-update: {}", event);
    }

    @Override
    public void sendPhysicalInvestResultCreated(PhysicalInvestResultCreateEvent event) {
        kafkaTemplate.send("physical-invest-result-create", event);
        log.info("Sent physical-invest-result-create: {}", event);
    }

    @Override
    public void sendPhysicalInvestResultUpdated(PhysicalInvestResultUpdateEvent event) {
        kafkaTemplate.send("physical-invest-result-update", event);
        log.info("Sent physical-invest-result-update: {}", event);
    }

    @Override
    public void sendFinancialInvestResultCreated(FinancialInvestResultCreateEvent event) {
        kafkaTemplate.send("financial-invest-result-create", event);
        log.info("Sent financial-invest-result-create: {}", event);
    }

    @Override
    public void sendFinancialInvestResultUpdated(FinancialInvestResultUpdateEvent event) {
        kafkaTemplate.send("financial-invest-result-update", event);
        log.info("Sent financial-invest-result-update: {}", event);
    }

    @Override
    public void sendForensicInvestResultCreated(ForensicInvestResultCreateEvent event) {
        kafkaTemplate.send("forensic-invest-result-create", event);
        log.info("Sent forensic-invest-result-create: {}", event);
    }

    @Override
    public void sendForensicInvestResultUpdated(ForensicInvestResultUpdateEvent event) {
        kafkaTemplate.send("forensic-invest-result-update", event);
        log.info("Sent forensic-invest-result-update: {}", event);
    }
}