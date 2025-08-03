package com.backend.investigationservice.kafka;

public interface EventPublisher {
    void send(String topic, Object event);
}