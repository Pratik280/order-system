package com.pratik.microservices.order_system.orders.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratik.microservices.order_system.common.config.JsonUtil;
import com.pratik.microservices.order_system.common.constants.OutboxStatus;
import com.pratik.microservices.order_system.common.exception.BusinessException;
import com.pratik.microservices.order_system.common.exception.ResourceNotFoundException;
import com.pratik.microservices.order_system.orders.dto.OrderRequest;
import com.pratik.microservices.order_system.orders.dto.OrderResponse;
import com.pratik.microservices.order_system.orders.entity.OrderEntity;
import com.pratik.microservices.order_system.orders.entity.OutboxEventEntity;
import com.pratik.microservices.order_system.orders.repository.OrderRepository;
import com.pratik.microservices.order_system.orders.repository.OutboxRepository;
import com.pratik.microservices.order_system.orders.service.OrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ModelMapper modelMapper;
    private final JsonUtil jsonUtil;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ModelMapper modelMapper,
            JsonUtil jsonUtil,
            OutboxRepository outboxRepository ){
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.jsonUtil = jsonUtil;
        this.outboxRepository = outboxRepository;
    }


    @Override
    public List<OrderResponse> getAllOrders(){
        List<OrderResponse> orders = orderRepository.findAll()
                .stream()
                .map(orderEntity -> modelMapper.map(orderEntity, OrderResponse.class))
                .toList();
        if(orders.isEmpty()){
            throw new ResourceNotFoundException("No orders found");
        }
        return orders;
    }

    @Override
    public OrderResponse getOrderById(Long id){
        return orderRepository.findById(id)
                .map(orderEntity -> modelMapper.map(orderEntity, OrderResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        OrderEntity orderEntity;
        try{
            orderEntity = modelMapper.map(orderRequest, OrderEntity.class);
        } catch (Exception e){
            throw new BusinessException("MAPPING_ERROR", "Failed to map order request");
        }

        try{
            OrderEntity saved = orderRepository.save(orderEntity);

            // outbox for dual write problem

            OutboxEventEntity event = new OutboxEventEntity();
            event.setEventType("ORDER_CREATED");
            event.setPayload(jsonUtil.convertToJson(saved));
            event.setStatus(OutboxStatus.NEW);
            outboxRepository.save(event);

            return modelMapper.map(saved, OrderResponse.class);
        } catch (Exception e){
            throw new BusinessException("DATA_INTEGRITY_VIOLATION", "Invalid Order Data");
        }
    }
}
