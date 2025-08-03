package com.backend.investigationservice.kafka.handler;

import com.backend.investigationservice.event.listener.*;
import com.backend.investigationservice.service.InvestigationResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvestigationEventHandler {

    private final InvestigationResultService resultService;

    public void handleDigitalCreate(DigitalInvestResultCreateEvent event) {
        log.info("Handling DigitalInvestResultCreateEvent: {}", event);
        resultService.saveDigitalResult(event.getResult());
    }

    public void handleDigitalUpdate(DigitalInvestResultUpdateEvent event) {
        log.info("Handling DigitalInvestResultUpdateEvent: {}", event);
        resultService.updateDigitalResult(event.getResult());
    }

    public void handlePhysicalCreate(PhysicalInvestResultCreateEvent event) {
        log.info("Handling PhysicalInvestResultCreateEvent: {}", event);
        resultService.savePhysicalResult(event.getResult());
    }

    public void handlePhysicalUpdate(PhysicalInvestResultUpdateEvent event) {
        log.info("Handling PhysicalInvestResultUpdateEvent: {}", event);
        resultService.updatePhysicalResult(event.getResult());
    }

    public void handleFinancialCreate(FinancialInvestResultCreateEvent event) {
        log.info("Handling FinancialInvestResultCreateEvent: {}", event);
        resultService.saveFinancialResult(event.getResult());
    }

    public void handleFinancialUpdate(FinancialInvestResultUpdateEvent event) {
        log.info("Handling FinancialInvestResultUpdateEvent: {}", event);
        resultService.updateFinancialResult(event.getResult());
    }

    public void handleForensicCreate(ForensicInvestResultCreateEvent event) {
        log.info("Handling ForensicInvestResultCreateEvent: {}", event);
        resultService.saveForensicResult(event.getResult());
    }

    public void handleForensicUpdate(ForensicInvestResultUpdateEvent event) {
        log.info("Handling ForensicInvestResultUpdateEvent: {}", event);
        resultService.updateForensicResult(event.getResult());
    }
}