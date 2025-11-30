package com.delivery.core.domain.model.karnel;

import com.delivery.ddd.ValueObject;
import lombok.Getter;

import java.util.List;

@Getter
public class Location extends ValueObject<Location> {

    public static final Integer minX = 1;
    public static final Integer minY = 1;
    public static final Integer maxX = 10;
    public static final Integer maxY = 10;

    private Integer x;
    private Integer y;

    private Location() {

    }

    public Location(Integer x, Integer y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Coordinates cannot be null.");
        }
        if (x < minX || x > maxX) {
            throw new IllegalArgumentException("X coordinate must be between 1 and 10, but was: " + x + ".");
        }
        if (y < minY || y > maxY) {
            throw new IllegalArgumentException("Y coordinate must be between 1 and 10, but was: " + y + ".");
        }

        this.x = x;
        this.y = y;
    }

    public Integer distanceTo(Location other) {
        if (other == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }

        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    protected List<Object> equalityComponents() {
        return List.of(x, y);
    }
}
