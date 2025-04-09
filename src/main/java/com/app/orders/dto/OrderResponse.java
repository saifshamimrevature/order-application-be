package com.app.orders.dto;

import com.app.orders.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<Item> items;

    @Data
    @Builder
    public static class Item {
        private String productName;
        private int quantity;
        private double price;
    }
}
