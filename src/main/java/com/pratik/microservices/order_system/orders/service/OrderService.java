package com.pratik.microservices.order_system.orders.service;

import com.pratik.microservices.order_system.orders.dto.OrderRequest;
import com.pratik.microservices.order_system.orders.dto.OrderResponse;
import com.pratik.microservices.order_system.orders.entity.OrderEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    public List<OrderResponse> getAllOrders();
    public OrderResponse getOrderById(Long id);
    public OrderResponse createOrder(OrderRequest orderRequest);
}
