package com.backend.investigationservice.kafka.listener;

import com.backend.investigationservice.event.listener.*;
import com.backend.investigationservice.kafka.handler.InvestigationEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvestigationKafkaListener {

    private final InvestigationEventHandler handler;

    @KafkaListener(
            topics = "digital-invest-result-create",
            groupId = "investigation-service-digital-invest-result-create",
            containerFactory = "digitalInvestResultCreateKafkaListenerFactory"
    )
    public void listenDigitalCreate(DigitalInvestResultCreateEvent event) {
        handler.handleDigitalCreate(event);
    }

    @KafkaListener(
            topics = "digital-invest-result-update",
            groupId = "investigation-service-digital-invest-result-update",
            containerFactory = "digitalInvestResultUpdateKafkaListenerFactory"
    )
    public void listenDigitalUpdate(DigitalInvestResultUpdateEvent event) {
        handler.handleDigitalUpdate(event);
    }

    @KafkaListener(
            topics = "physical-invest-result-create",
            groupId = "investigation-service-physical-invest-result-create",
            containerFactory = "physicalInvestResultCreateKafkaListenerFactory"
    )
    public void listenPhysicalCreate(PhysicalInvestResultCreateEvent event) {
        handler.handlePhysicalCreate(event);
    }

    @KafkaListener(
            topics = "physical-invest-result-update",
            groupId = "investigation-service-physical-invest-result-update",
            containerFactory = "physicalInvestResultUpdateKafkaListenerFactory"
    )
    public void listenPhysicalUpdate(PhysicalInvestResultUpdateEvent event) {
        handler.handlePhysicalUpdate(event);
    }

    @KafkaListener(
            topics = "financial-invest-result-create",
            groupId = "investigation-service-financial-invest-result-create",
            containerFactory = "financialInvestResultCreateKafkaListenerFactory"
    )
    public void listenFinancialCreate(FinancialInvestResultCreateEvent event) {
        handler.handleFinancialCreate(event);
    }

    @KafkaListener(
            topics = "financial-invest-result-update",
            groupId = "investigation-service-financial-invest-result-update",
            containerFactory = "financialInvestResultUpdateKafkaListenerFactory"
    )
    public void listenFinancialUpdate(FinancialInvestResultUpdateEvent event) {
        handler.handleFinancialUpdate(event);
    }

    @KafkaListener(
            topics = "forensic-invest-result-create",
            groupId = "investigation-service-forensic-invest-result-create",
            containerFactory = "forensicInvestResultCreateKafkaListenerFactory"
    )
    public void listenForensicCreate(ForensicInvestResultCreateEvent event) {
        handler.handleForensicCreate(event);
    }

    @KafkaListener(
            topics = "forensic-invest-result-update",
            groupId = "investigation-service-forensic-invest-result-update",
            containerFactory = "forensicInvestResultUpdateKafkaListenerFactory"
    )
    public void listenForensicUpdate(ForensicInvestResultUpdateEvent event) {
        handler.handleForensicUpdate(event);
    }
}