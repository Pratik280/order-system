package com.pratik.microservices.order_system.orders.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratik.microservices.order_system.common.constants.OutboxStatus;
import com.pratik.microservices.order_system.orders.dto.OrderResponse;
import com.pratik.microservices.order_system.orders.entity.OutboxEventEntity;
import com.pratik.microservices.order_system.orders.repository.OutboxRepository;
import com.pratik.microservices.order_system.orders.service.OutboxPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OutboxPublisherImpl implements OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OutboxPublisherImpl(OutboxRepository outboxRepository,
                           KafkaTemplate<String, OrderResponse> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishBatch() {

        List<OutboxEventEntity> events = outboxRepository.fetchBatchForUpdate(10);
        for(OutboxEventEntity event: events){
            try{
                OrderResponse orderResponse = objectMapper.readValue(event.getPayload(), OrderResponse.class);
                CompletableFuture<SendResult<String, OrderResponse>> future = kafkaTemplate.send(
                        "order-topic",
                        event.getId().toString(),
                        orderResponse
                );

                future.whenComplete((result, ex) -> {
                    if(ex != null){
                        log.error("Failed to send order {} to kafka : {}",
                                event.getPayload(),
                                ex.getMessage());
                    } else {
                        log.info("Order {} sent successfully to pattern {}, offset {}",
                                event.getPayload(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()
                        );
                    }
                });
                event.setStatus(OutboxStatus.SENT);
                outboxRepository.save(event);
            } catch (Exception e){
                event.setStatus(OutboxStatus.NEW);
            }
        }
    }
}
