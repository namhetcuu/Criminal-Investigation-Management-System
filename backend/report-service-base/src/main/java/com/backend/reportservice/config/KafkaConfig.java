package com.backend.reportservice.config;

import com.backend.reportservice.dto.response.ReportDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

  @Bean
  public ProducerFactory<String, ReportDto> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    // Configure Kafka bootstrap servers
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
    // Set key and value serializers
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    // Enable idempotence to prevent duplicate messages
    configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    // Disable type info headers for cleaner serialization
    configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
    // Configure retries for transient failures
    configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
    // Set retry backoff to 100ms
    configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 100);
    // Ensure message delivery reliability
    configProps.put(ProducerConfig.ACKS_CONFIG, "all");
    // Optimize throughput with batching
    configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
    configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, ReportDto> kafkaTemplate() {
    // Create KafkaTemplate with configured producer factory
    return new KafkaTemplate<>(producerFactory());
  }
}