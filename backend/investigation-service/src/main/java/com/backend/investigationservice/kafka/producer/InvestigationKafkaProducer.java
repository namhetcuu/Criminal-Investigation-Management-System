package com.backend.investigationservice.kafka.producer;

import com.backend.investigationservice.event.listener.*;

public interface InvestigationKafkaProducer {

    void sendDigitalInvestResultCreated(DigitalInvestResultCreateEvent event);
    void sendDigitalInvestResultUpdated(DigitalInvestResultUpdateEvent event);

    void sendPhysicalInvestResultCreated(PhysicalInvestResultCreateEvent event);
    void sendPhysicalInvestResultUpdated(PhysicalInvestResultUpdateEvent event);

    void sendFinancialInvestResultCreated(FinancialInvestResultCreateEvent event);
    void sendFinancialInvestResultUpdated(FinancialInvestResultUpdateEvent event);

    void sendForensicInvestResultCreated(ForensicInvestResultCreateEvent event);
    void sendForensicInvestResultUpdated(ForensicInvestResultUpdateEvent event);
}