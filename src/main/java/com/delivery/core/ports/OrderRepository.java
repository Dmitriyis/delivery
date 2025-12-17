package com.delivery.core.ports;

import com.delivery.core.domain.model.order.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {
    void addOrder(Order order);

    void updateOrder(Order order);

    Order getById(UUID orderId);

    Order getRandomOneOrderWithStatusCreated();

    List<Order> getAllOrderWithStatusAssigned();
}
