package com.backend.investigationservice.config.kafka;

import com.backend.investigationservice.event.listener.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaListenerFactoryProvider {

    private <T> ConsumerFactory<String, T> buildConsumerFactory(Class<T> clazz, String groupId) {
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, groupId,
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> buildFactory(Class<T> clazz, String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(buildConsumerFactory(clazz, groupId));
        return factory;
    }

    // === Kafka Listener Factory beans for each event ===

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CaseCreatedEvent> caseCreatedKafkaListenerFactory() {
        return buildFactory(CaseCreatedEvent.class, "investigation-service-case-created");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CaseDeletedEvent> caseDeletedKafkaListenerFactory() {
        return buildFactory(CaseDeletedEvent.class, "investigation-service-case-deleted");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DigitalInvestResultCreateEvent> digitalInvestResultCreateKafkaListenerFactory() {
        return buildFactory(DigitalInvestResultCreateEvent.class, "investigation-service-digital-invest-result-create");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DigitalInvestResultUpdateEvent> digitalInvestResultUpdateKafkaListenerFactory() {
        return buildFactory(DigitalInvestResultUpdateEvent.class, "investigation-service-digital-invest-result-update");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FinancialInvestResultCreateEvent> financialInvestResultCreateKafkaListenerFactory() {
        return buildFactory(FinancialInvestResultCreateEvent.class, "investigation-service-financial-invest-result-create");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FinancialInvestResultUpdateEvent> financialInvestResultUpdateKafkaListenerFactory() {
        return buildFactory(FinancialInvestResultUpdateEvent.class, "investigation-service-financial-invest-result-update");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ForensicInvestResultCreateEvent> forensicInvestResultCreateKafkaListenerFactory() {
        return buildFactory(ForensicInvestResultCreateEvent.class, "investigation-service-forensic-invest-result-create");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ForensicInvestResultUpdateEvent> forensicInvestResultUpdateKafkaListenerFactory() {
        return buildFactory(ForensicInvestResultUpdateEvent.class, "investigation-service-forensic-invest-result-update");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PhysicalInvestResultCreateEvent> physicalInvestResultCreateKafkaListenerFactory() {
        return buildFactory(PhysicalInvestResultCreateEvent.class, "investigation-service-physical-invest-result-create");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PhysicalInvestResultUpdateEvent> physicalInvestResultUpdateKafkaListenerFactory() {
        return buildFactory(PhysicalInvestResultUpdateEvent.class, "investigation-service-physical-invest-result-update");
    }

}