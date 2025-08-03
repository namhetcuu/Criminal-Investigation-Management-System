package com.Evidence_Service.config.kafka;

import com.Evidence_Service.event.listener.*;
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
        return buildFactory(CaseCreatedEvent.class, "evidence-service-case-created");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CaseDeletedEvent> caseDeletedKafkaListenerFactory() {
        return buildFactory(CaseDeletedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReportCreatedEvent> reportCreatedKafkaListenerFactory() {
        return buildFactory(ReportCreatedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReportDeletedEvent> reportDeletedKafkaListenerFactory() {
        return buildFactory(ReportDeletedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SuspectDeletedEvent> suspectDeletedKafkaListenerFactory() {
        return buildFactory(SuspectDeletedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AnalysisResultEvent> analysisResultKafkaListenerFactory() {
        return buildFactory(AnalysisResultEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SuspectAssignedEvent> suspectAssignedKafkaListenerFactory() {
        return buildFactory(SuspectAssignedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CaseAssignedEvent> caseAssignedKafkaListenerFactory() {
        return buildFactory(CaseAssignedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WarrantAssignedEvent> warrantAssignedKafkaListenerFactory() {
        return buildFactory(WarrantAssignedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WarrantDeletedEvent> warrantDeletedKafkaListenerFactory() {
        return buildFactory(WarrantDeletedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResultInvestAssignedEvent> investigationAssignedKafkaListenerFactory() {
        return buildFactory(ResultInvestAssignedEvent.class, "evidence-service");
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeleteInvestResultEvent> investigationDeletedKafkaListenerFactory() {
        return buildFactory(DeleteInvestResultEvent.class, "evidence-service");
    }
}
