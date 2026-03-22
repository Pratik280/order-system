package com.pratik.microservices.order_system.common.scheduler;

import com.pratik.microservices.order_system.orders.service.OutboxPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxScheduler {

    private final OutboxPublisher publisher;

    public OutboxScheduler(OutboxPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedDelay = 5000)
    public void run() {
        publisher.publishBatch();
    }
}
