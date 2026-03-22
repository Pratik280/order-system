package com.pratik.microservices.order_system.orders.entity;

import com.pratik.microservices.order_system.common.constants.OutboxStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OutboxEventEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    @Column(columnDefinition =  "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;
}
