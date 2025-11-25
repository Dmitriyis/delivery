package com.delivery.core.domain.model.karnel;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class LocationTest {

    @Test
    public void createLocationSuccessfulTest() {
        // Arrange
        // Act
        Location location = new Location(4, 7);

        // Assert
        assertThat(location).isNotNull();
    }

    @Test
    public void createLocationFailTest() {
        // Arrange
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(null, 5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Location(999, 4);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Location(4, 999);
        });
    }

    @Test
    public void distanceToSuccessfulTest() {
        // Arrange
        Location locationFirst = new Location(2, 6);
        Location locationSecond = new Location(4, 9);

        // Act
        Integer distanceBetweenLocations = locationFirst.distanceTo(locationSecond);

        // Assert
        assertEquals(5, distanceBetweenLocations);

    }

    @Test
    public void distanceToFailTest() {
        // Arrange
        Location locationFirst = new Location(2, 5);

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            locationFirst.distanceTo(null);
        });
    }

}