package com.pratik.microservices.order_system.orders.service.impl;

import com.pratik.microservices.order_system.common.exception.BusinessException;
import com.pratik.microservices.order_system.common.exception.ResourceNotFoundException;
import com.pratik.microservices.order_system.orders.dto.OrderRequest;
import com.pratik.microservices.order_system.orders.dto.OrderResponse;
import com.pratik.microservices.order_system.orders.entity.OrderEntity;
import com.pratik.microservices.order_system.orders.repository.OrderRepository;
import com.pratik.microservices.order_system.orders.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper){
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
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
    public OrderResponse createOrder(OrderRequest orderRequest) {

        OrderEntity orderEntity;
        try{
            orderEntity = modelMapper.map(orderRequest, OrderEntity.class);
        } catch (Exception e){
            throw new BusinessException("MAPPING_ERROR", "Failed to map order request");
        }

        try{
            OrderEntity saved = orderRepository.save(orderEntity);
            return modelMapper.map(saved, OrderResponse.class);
        } catch (Exception e){
            throw new BusinessException("DATA_INTEGRITY_VIOLATION", "Invalid Order Data");
        }
    }
}
