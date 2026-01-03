package com.pratik.microservices.order_system.orders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    private String productName;
}