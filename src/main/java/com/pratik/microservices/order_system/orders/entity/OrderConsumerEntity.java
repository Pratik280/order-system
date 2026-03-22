package com.pratik.microservices.order_system.orders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consumedorders")
@Getter
@Setter
@NoArgsConstructor
public class OrderConsumerEntity {

    @Id
    private long id;

    private String productName;
}