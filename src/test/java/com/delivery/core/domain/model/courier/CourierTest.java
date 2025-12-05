package com.delivery.core.domain.model.courier;

import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.domain.model.order.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourierTest {

    private static Stream<Object> invalidCourierParams() {
        return Stream.of(
                new Object[]{null, 2, new Location(1, 1)},
                new Object[]{"", 2, new Location(1, 1)},
                new Object[]{"   ", 2, new Location(1, 1)},
                new Object[]{"St", null, new Location(1, 1)},
                new Object[]{"St", -1, new Location(1, 1)},
                new Object[]{"St", 2, null}
        );
    }

    private static Stream<Object> invalidAddStoragePlaceParams() {
        return Stream.of(
                new Object[]{null, 10},
                new Object[]{"Пакет", null},
                new Object[]{"Пакет", -1}
        );
    }

    @Test
    void createCourier_SuccessfulTest() {
        // Arrange
        Location location = new Location(1, 1);

        // Act
        Courier courier = new Courier("St", 2, location);

        // Assert
        assertNotNull(courier);
        assertEquals(1, courier.getStoragePlaces().size());
        assertEquals("Сумка", courier.getStoragePlaces().get(0).getName());
        assertEquals(10, courier.getStoragePlaces().get(0).getTotalVolume());
    }

    @ParameterizedTest
    @MethodSource("invalidCourierParams")
    public void createCourier_FailTest(String name, Integer speed, Location location) {
        // Arrange
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Courier(name, speed, location);
        });
    }

    @Test
    public void addStoragePlace_SuccessfulTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        courier.addStoragePlace("Ящик", 5);

        // Assert
        Optional<StoragePlace> storagePlaceBox = courier.getStoragePlaces()
                .stream()
                .filter(storagePlace -> {
                    return storagePlace.getName().equals("Ящик");
                }).findFirst();

        assertEquals(2, courier.getStoragePlaces().size());
        assertTrue(storagePlaceBox.isPresent());
    }

    @ParameterizedTest
    @MethodSource("invalidAddStoragePlaceParams")
    public void addStoragePlace_FailTest(String name, Integer volume) {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            courier.addStoragePlace(name, volume);
        });
    }

    @Test
    public void canTake_OrderSuccessfulTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        Boolean hasStorageSpace = courier.canTakeOrder(order);

        // Assert
        assertTrue(hasStorageSpace);
    }

    @Test
    public void canTake_OrderIsNullFailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            courier.canTakeOrder(null);
            ;
        });
    }

    @Test
    public void canTakeOrder_ValueOverAcceptableFreeSpaceFailTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 20);
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        Boolean hasStorageSpace = courier.canTakeOrder(order);

        // Assert
        assertFalse(hasStorageSpace);
    }

    @Test
    public void takeOrder_SuccessfulTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        courier.takeOrder(order);

        // Assert
        assertEquals(order.getId(), courier.getStoragePlaces().get(0).getOrderId());
    }

    @Test
    public void takeOrder_OrderIsNullFailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            courier.takeOrder(null);
        });
    }


    @Test
    public void completedOrder_OrderIsNullFailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            courier.completedOrder(null);
        });
    }

    @Test
    public void completedOrder_OrderInvalidFailTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);
        Order orderInvalid = new Order(UUID.randomUUID(), new Location(1, 1), 5);
        Courier courier = new Courier("St", 2, new Location(1, 1));

        courier.takeOrder(order);

        // Act
        courier.completedOrder(orderInvalid);

        // Assert
        UUID orderIdStoragePlaces = courier.getStoragePlaces().get(0).getOrderId();

        assertNotNull(orderIdStoragePlaces);
    }

    @Test
    public void completedOrder_SuccessfulTest() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), new Location(1, 1), 5);
        Courier courier = new Courier("St", 2, new Location(1, 1));

        courier.takeOrder(order);

        // Act
        courier.completedOrder(order);

        // Assert
        UUID orderIdStoragePlaces = courier.getStoragePlaces().get(0).getOrderId();

        assertNull(orderIdStoragePlaces);
    }

    @Test
    public void move_SuccessfulTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));
        Location location = new Location(5, 5);

        // Act
        courier.move(location);

        // Assert
        assertEquals(3, courier.getLocation().getX());

        // Act
        courier.move(location);

        // Assert
        assertEquals(5, courier.getLocation().getX());

        // Act
        courier.move(location);

        // Assert
        assertEquals(3, courier.getLocation().getY());

        // Act
        courier.move(location);

        // Assert
        assertEquals(5, courier.getLocation().getY());
    }

    @Test
    public void move_LocationIsNullFailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            courier.move(null);
        });
    }

    @Test
    public void calculateTimeToLocation_LocationIsnullFailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            courier.calculateTimeToLocation(null);
        });
    }

    @Test
    public void calculateTimeToLocation_LocationIsnullFqailTest() {
        // Arrange
        Courier courier = new Courier("St", 2, new Location(1, 1));
        Location location = new Location(5, 5);

        // Act
        Double distancesBetweenLocations = courier.calculateTimeToLocation(location);

        // Assert
        assertEquals(4, distancesBetweenLocations);
    }
}