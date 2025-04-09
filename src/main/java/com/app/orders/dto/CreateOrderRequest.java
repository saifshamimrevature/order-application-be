package com.app.orders.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private String productName;
        private int quantity;
        private double price;
    }
}
