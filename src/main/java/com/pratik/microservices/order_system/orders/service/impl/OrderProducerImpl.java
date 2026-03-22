package com.pratik.microservices.order_system.orders.service.impl;

import com.pratik.microservices.order_system.orders.dto.OrderRequest;
import com.pratik.microservices.order_system.orders.dto.OrderResponse;
import com.pratik.microservices.order_system.orders.service.OrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class OrderProducerImpl implements OrderProducer {

    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;

    public OrderProducerImpl(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderResponse orderResponse){

        CompletableFuture<SendResult<String, OrderResponse>> future =
                kafkaTemplate.send(
                        "order-topic",
                        String.valueOf(orderResponse.getId()),
                        orderResponse
                );

        future.whenComplete((result, ex) -> {
                            if(ex != null){
                                log.error("Failed to send order {} to kafka : {}",
                                        orderResponse.getId(),
                                        ex.getMessage());
                            } else {
                                log.info("Order {} sent successfully to pattern {}, offset {}",
                                        orderResponse.getId(),
                                        result.getRecordMetadata().partition(),
                                        result.getRecordMetadata().offset()
                                );
                            }
                        });
        log.info("Send to kafka : {}", orderResponse.getProductName());
    }
}
