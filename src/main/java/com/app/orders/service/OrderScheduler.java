package com.app.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;

    @Scheduled(fixedRate = 300000) // 5 minutes
    public void updatePendingOrders() {
        orderService.updatePendingOrdersToProcessing();
    }

}
