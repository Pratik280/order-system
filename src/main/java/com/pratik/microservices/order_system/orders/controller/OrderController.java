package com.pratik.microservices.order_system.orders.controller;

import com.pratik.microservices.order_system.common.response.BaseResponse;
import com.pratik.microservices.order_system.common.util.ResponseBuilder;
import com.pratik.microservices.order_system.orders.dto.OrderRequest;
import com.pratik.microservices.order_system.orders.dto.OrderResponse;
import com.pratik.microservices.order_system.orders.entity.OrderEntity;
import com.pratik.microservices.order_system.orders.repository.OrderRepository;
import com.pratik.microservices.order_system.orders.service.OrderProducer;
import com.pratik.microservices.order_system.orders.service.OrderService;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    // Service Obj
    private final OrderService orderService;
    private final OrderProducer orderProducer;

    // constructor
    public OrderController(OrderService orderService, OrderProducer orderProducer){
        this.orderService = orderService;
        this.orderProducer = orderProducer;
    }

    // get all order
    @GetMapping
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAllOrders(){
//        return orderService.getAllOrders();
        return ResponseBuilder.success(
                HttpStatus.OK,
                "All orders fetch successfully",
                orderService.getAllOrders()
        );
    }

    // get order by id
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderResponse>> getOrderById(@PathVariable Long id){
//        return orderService.getOrderById(id);
        return ResponseBuilder.success(
                HttpStatus.OK,
                "Order fetch successfully",
                orderService.getOrderById(id)
        );
    }

    // post order
    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest orderRequest){

        OrderResponse response = orderService.createOrder(orderRequest);


//        orderProducer.sendOrder(response);

        return ResponseBuilder.success(
                HttpStatus.OK,
                "Order created successfully",
                response
        );
    }
}

//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//    private OrderRepository orderRepository;
//    public OrderController(OrderRepository orderRepository){
//        this.orderRepository = orderRepository;
//    }
//
//    // POST -> Create new order
//    @PostMapping
//    public OrderEntity createOrder(@RequestBody OrderEntity order){
//        return orderRepository.save(order);
//    }
//
//    // GET -> Fetch all order
//    @GetMapping
//    public List<OrderEntity> getAllOrders(){
//        return orderRepository.findAll();
//    }
//
//    // GET -> Fetch order by id
//    @GetMapping("/{id}")
//    public OrderEntity getOrderById(@PathVariable Long id){
//        return orderRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//    }
//}
