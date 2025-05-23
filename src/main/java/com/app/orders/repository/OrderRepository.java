package com.app.orders.repository;

import com.app.orders.model.Order;
import com.app.orders.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
