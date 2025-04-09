package com.app.orders.controller;

import com.app.orders.dto.CreateOrderRequest;
import com.app.orders.dto.OrderResponse;
import com.app.orders.dto.UpdateOrderStatusRequest;
import com.app.orders.model.OrderStatus;
import com.app.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")  // ✅ THIS defines the base path!
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders(@RequestParam(required = false) OrderStatus status) {
        return orderService.getAllOrders(status);
    }

    @DeleteMapping("/{id}")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(orderService.updateOrderStatus(id, request.getStatus()));
    }

}
