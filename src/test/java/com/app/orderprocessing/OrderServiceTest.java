package com.app.orderprocessing;

import com.app.orders.dto.CreateOrderRequest;
import com.app.orders.dto.OrderResponse;
import com.app.orders.model.OrderStatus;
import com.app.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = Mockito.mock(OrderService.class);
    }

    @Test
    public void testCreateOrder() {
        // Create request
        CreateOrderRequest request = new CreateOrderRequest();
        CreateOrderRequest.OrderItemDTO item = new CreateOrderRequest.OrderItemDTO();
        item.setProductName("Keyboard");
        item.setQuantity(2);
        item.setPrice(49.99);
        request.setItems(List.of(item));

        // Mock expected response
        OrderResponse expectedResponse = OrderResponse.builder()
                .orderId(1L)
                .status(OrderStatus.PENDING)
                .items(List.of(OrderResponse.Item.builder()
                        .productName("Keyboard")
                        .quantity(2)
                        .price(49.99)
                        .build()))
                .build();

        when(orderService.createOrder(any())).thenReturn(expectedResponse);

        // Invoke
        OrderResponse actualResponse = orderService.createOrder(request);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(OrderStatus.PENDING, actualResponse.getStatus());
        assertEquals(1L, actualResponse.getOrderId());
        assertEquals(1, actualResponse.getItems().size());
        assertEquals("Keyboard", actualResponse.getItems().get(0).getProductName());
    }
}
