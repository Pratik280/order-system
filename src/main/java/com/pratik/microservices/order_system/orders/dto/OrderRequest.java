package com.pratik.microservices.order_system.orders.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    @NotBlank(message = "productName is mandatory")
    private String productName;
}
