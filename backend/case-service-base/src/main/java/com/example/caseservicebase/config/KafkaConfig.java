package com.example.caseservicebase.config;

import com.example.caseservicebase.dto.shared.ReportDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

  public ConsumerFactory<String, ReportDto> consumerFactory() {
    JsonDeserializer<ReportDto> deserializer = new JsonDeserializer<>(ReportDto.class);
    deserializer.addTrustedPackages("*"); // hoặc cụ thể package nếu cần
    deserializer.setUseTypeMapperForKey(false);

    return new DefaultKafkaConsumerFactory<>(
            Map.of(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092",
                    ConsumerConfig.GROUP_ID_CONFIG, "case-service-group",
                    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
            ),
            new org.apache.kafka.common.serialization.StringDeserializer(),
            deserializer
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, ReportDto> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, ReportDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}