package com.delivery.core.domain.model.order;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.karnel.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    private static Stream<Object> invalidOrderParams() {
        return Stream.of(
                new Object[]{null, new Location(1,1), 10},
                new Object[]{UUID.randomUUID(), null, 10},
                new Object[]{UUID.randomUUID(), new Location(1,1), null},
                new Object[]{UUID.randomUUID(), new Location(1,1), -1}
        );
    }

    @Test
    void createOrderSuccessfulTest() {
        // Arrange
        UUID id = UUID.randomUUID();
        Location location = new Location(1, 1);

        // Act
        Order order = new Order(id, location, 10);

        // Assert
        assertNotNull(order);
    }

    @ParameterizedTest
    @MethodSource("invalidOrderParams")
    public void createOrderFailTest(UUID id, Location location, Integer totalValue) {
        // Arrange
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Order(id, location, totalValue);
        });
    }

    @Test
    public void assignedSuccessfulTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);
        Courier courier = new Courier("St", 2, new Location(1, 2));

        // Act
        order.assigned(courier);

        // Assert
        assertEquals(OrderStatus.ASSIGNED, order.getOrderStatus());
        assertEquals(order.getCourierId(), courier.getId());
    }

    @Test
    public void assignedCourierNullFailTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            order.assigned(null);
        });
    }

    @Test
    public void assignedOrderStatusAssignedFailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 2));
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);
        order.assigned(courier);

        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> {
            order.assigned(courier);
        });
    }

    @Test
    public void assignedOrderStatusCompletedFailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 2));
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);
        order.assigned(courier);
        order.completed();

        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> {
            order.assigned(courier);
        });
    }

    @Test
    public void completedOrderStatusCompletedFailTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);

        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> {
            order.completed();
        });
    }

    @Test
    public void completedOrderStatusCreatedFailTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);

        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> {
            order.completed();
        });
    }

    @Test
    public void completedSuccessfulTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 10);
        Courier courier = new Courier("St", 2, new Location(1, 2));
        order.assigned(courier);

        // Act
        order.completed();

        // Assert
        assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
    }
}