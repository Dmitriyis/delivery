package com.delivery.core.domain.model.courier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoragePlaceTest {

    private static Stream<Arguments> invalidStoragePlaceParams() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of("test", null),
                Arguments.of("test", -5)
        );
    }

    private static Stream<Arguments> validStoragePlaceParams() {
        return Stream.of(
                Arguments.of("test", 1),
                Arguments.of("test", 10)
        );
    }
    

    private static Stream<Arguments> invalidStoreParams() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of(UUID.randomUUID(), null),
                Arguments.of(UUID.randomUUID(), -5),
                Arguments.of(UUID.randomUUID(), 6)
        );
    }

    private static Stream<Object> invalidClearParams() {
        return Stream.of(
                new Object[]{null},
                new Object[]{UUID.randomUUID()}
        );
    }


    @ParameterizedTest
    @MethodSource("validStoragePlaceParams")
    void createStoragePlaceSuccessfulTest(String name, Integer totalValue) {
        // Arrange
        // Act
        StoragePlace storagePlace = new StoragePlace(name, totalValue);

        // Assert
        assertNotNull(storagePlace);
    }

    @ParameterizedTest
    @MethodSource("invalidStoragePlaceParams")
    public void createStoragePlaceFailTest(String name, Integer totalValue) {
        // Arrange
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new StoragePlace(name, totalValue);
        });
    }

    @Test
    public void storeSuccessfulTest() {
        // Arrange
        StoragePlace storagePlace = new StoragePlace("test", 5);
        UUID orderId = UUID.randomUUID();

        // Act
        storagePlace.store(orderId, 3);

        // Assert
        assertEquals(orderId, storagePlace.getOrderId());
    }

    @ParameterizedTest
    @MethodSource("invalidStoreParams")
    public void storeFailTest(UUID orderId, Integer value) {
        // Arrange
        StoragePlace storagePlace = new StoragePlace("test", 5);

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            storagePlace.store(orderId, value);
        });
    }

    @Test
    public void clearSuccessfulTest() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        StoragePlace storagePlace = new StoragePlace("test", 5);

        storagePlace.store(orderId, 3);

        // Act
        storagePlace.clear(orderId);

        // Assert
        assertFalse(storagePlace.isOccupied());
    }

    @ParameterizedTest
    @MethodSource("invalidClearParams")
    public void clearFailTest(UUID orderIdUncorrected) {
        // Arrange
        UUID orderId = UUID.randomUUID();
        StoragePlace storagePlace = new StoragePlace("test", 5);

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            storagePlace.clear(orderIdUncorrected);
        });
    }
}