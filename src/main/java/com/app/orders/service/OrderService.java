package com.app.orders.service;

import com.app.orders.dto.CreateOrderRequest;
import com.app.orders.dto.OrderResponse;
import com.app.orders.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long id);
    List<OrderResponse> getAllOrders(OrderStatus status);
    void cancelOrder(Long id);
    void updatePendingOrdersToProcessing();

    OrderResponse updateOrderStatus(Long id, OrderStatus newStatus);
}
