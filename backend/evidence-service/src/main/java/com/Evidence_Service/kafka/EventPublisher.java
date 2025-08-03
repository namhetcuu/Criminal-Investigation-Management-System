package com.Evidence_Service.kafka;

public interface EventPublisher {
    void send(String topic, Object event);
}

