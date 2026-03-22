package com.pratik.microservices.order_system.common.scheduler;

import com.pratik.microservices.order_system.orders.dto.OrderResponse;
import com.pratik.microservices.order_system.orders.entity.OrderConsumerEntity;
import com.pratik.microservices.order_system.orders.repository.OrderConsumerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

    private final OrderConsumerRepository orderConsumerRepository;

    public OrderConsumer(OrderConsumerRepository orderConsumerRepository){
        this.orderConsumerRepository = orderConsumerRepository;
    }

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void orderConsume(OrderResponse orderResponse) throws Exception{
        try {
            OrderConsumerEntity orderConsumerEntity = new OrderConsumerEntity();
            orderConsumerEntity.setId(orderResponse.getId());
            orderConsumerEntity.setProductName(orderResponse.getProductName());

            orderConsumerRepository.save(orderConsumerEntity);

            log.info("Order saved from consumer : {}", orderResponse.getId());
        } catch (Exception e){
            log.info("Duplicate message, skipping: {}", orderResponse.getId());
        }
    }
}
