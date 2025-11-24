package com.delivery.core.domain.model.karnel;

import com.delivery.ddd.ValueObject;

import java.util.List;

public class Location extends ValueObject<Location> {
    private Integer x;
    private Integer y;

    private Location() {

    }

    private Location(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Location create(Integer x, Integer y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        if (x < 1 || x > 10) {
            throw new IllegalArgumentException("X coordinate must be between 1 and 10, but was: " + x);
        }
        if (y < 1 || y > 10) {
            throw new IllegalArgumentException("Y coordinate must be between 1 and 10, but was: " + y);
        }
        return new Location(x, y);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer distanceTo(Location other) {
        if (other == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }

        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    protected List<Object> equalityComponents() {
        return List.of(x, y);
    }
}
