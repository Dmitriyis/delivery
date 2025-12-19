package com.delivery.core.ports;

import com.delivery.core.AbstractIntegrationTest;
import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.domain.model.order.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Test
    public void addOrderTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);

        // Act
        orderRepository.addOrder(order);

        // Assert
        Order orderFromDb = orderRepository.getById(order.getId());

        assertNotNull(orderFromDb);
        assertEquals(orderFromDb.getId(), order.getId());
    }

    @Test
    public void updateOrderTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));
        courierRepository.addCourier(courier);

        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);
        order.assigned(courier);
        courier.takeOrder(order);
        orderRepository.addOrder(order);

        // Act
        orderRepository.updateOrder(order);

        // Assert
        Order orderFromDb = orderRepository.getById(order.getId());

        assertNotNull(orderFromDb.getCourierId());
        assertEquals(orderFromDb.getCourierId(), courier.getId());
    }
}