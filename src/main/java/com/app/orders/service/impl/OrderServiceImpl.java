package com.app.orders.service.impl;

import com.app.orders.dto.CreateOrderRequest;
import com.app.orders.dto.OrderResponse;
import com.app.orders.exception.InvalidOrderOperationException;
import com.app.orders.exception.OrderNotFoundException;
import com.app.orders.model.Order;
import com.app.orders.model.OrderItem;
import com.app.orders.model.OrderStatus;
import com.app.orders.repository.OrderRepository;
import com.app.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> items = new ArrayList<>();
        for (var itemDto : request.getItems()) {
            OrderItem item = new OrderItem(itemDto.getProductName(), itemDto.getQuantity(), itemDto.getPrice());
            item.setOrder(order); // Set the back-reference
            items.add(item);
        }

        order.setItems(items);

        order = orderRepository.save(order);
        return toResponse(order);
    }


    @Override
    public OrderResponse getOrderById(Long id) {
        return toResponse(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id)));
    }

    @Override
    public List<OrderResponse> getAllOrders(OrderStatus status) {
        List<Order> orders = (status != null)
                ? orderRepository.findByStatus(status)
                : orderRepository.findAll();

        return orders.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderOperationException("Only PENDING orders can be canceled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public void updatePendingOrdersToProcessing() {
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);

        pendingOrders.forEach(order -> order.setStatus(OrderStatus.PROCESSING));
        orderRepository.saveAll(pendingOrders);
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        // Allowed transitions
        switch (order.getStatus()) {
            case PROCESSING:
                if (newStatus != OrderStatus.SHIPPED) throw new InvalidOrderOperationException("Can only move from PROCESSING to SHIPPED");
                break;
            case SHIPPED:
                if (newStatus != OrderStatus.DELIVERED) throw new InvalidOrderOperationException("Can only move from SHIPPED to DELIVERED");
                break;
            default:
                throw new InvalidOrderOperationException("Invalid status transition");
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
        return toResponse(order);
    }


    // Helper method to convert Order to OrderResponse DTO
    private OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(item -> OrderResponse.Item.builder()
                                .productName(item.getProductName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
