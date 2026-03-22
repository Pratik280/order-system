package com.pratik.microservices.order_system.orders.service;

public interface OutboxPublisher {
    void publishBatch();
}
