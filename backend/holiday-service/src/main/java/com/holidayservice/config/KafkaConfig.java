package com.holidayservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {
   /**
    * Creates Kafka topics for the holiday service.
    * 
    * @return NewTopic instances for each topic.
    */
   @Bean
   public NewTopic holidayCreatedTopic() {
      return TopicBuilder.name("holiday.created")
            .partitions(3)
            .replicas(1)
            .build();
   }

   @Bean
   public NewTopic holidayApprovedTopic() {
      return TopicBuilder.name("holiday.approved")
            .partitions(3)
            .replicas(1)
            .build();
   }
}