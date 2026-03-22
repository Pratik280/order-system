package com.pratik.microservices.order_system.orders.service;

import com.pratik.microservices.order_system.orders.dto.OrderRequest;
import com.pratik.microservices.order_system.orders.dto.OrderResponse;

public interface OrderProducer {
    public void sendOrder(OrderResponse orderResponse);
}
