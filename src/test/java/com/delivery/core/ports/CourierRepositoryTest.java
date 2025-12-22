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

class CourierRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Test
    void addCourierTest() throws Exception {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        courierRepository.addCourier(courier);

        // Assert
        Courier courierFromDb = courierRepository.getById(courier.getId());

        assertNotNull(courierFromDb);
        assertEquals(courierFromDb.getId(), courier.getId());
        assertNotNull(courierFromDb.getStoragePlaces());
        assertEquals(courierFromDb.getStoragePlaces().size(), 1);
    }

    @Test
    void updateCourierTest() throws Exception {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));
        courierRepository.addCourier(courier);

        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);
        order.assigned(courier);
        courier.takeOrder(order);
        orderRepository.addOrder(order);

        // Act
        courierRepository.updateCourier(courier);

        // Assert
        Courier courierFromDb = courierRepository.getById(courier.getId());

        assertNotNull(courierFromDb.getStoragePlaces().get(0).getOrderId());
    }
}