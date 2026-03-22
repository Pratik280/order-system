package com.pratik.microservices.order_system.orders.repository;

import com.pratik.microservices.order_system.orders.entity.OrderConsumerEntity;
import com.pratik.microservices.order_system.orders.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//public interface OrderRepository extends JpaRepository<OrderEntity, Long> { }

public interface OrderConsumerRepository extends JpaRepository<OrderConsumerEntity, Long>{ }