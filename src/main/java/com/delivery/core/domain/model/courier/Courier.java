package com.delivery.core.domain.model.courier;

import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.domain.model.order.Order;
import com.delivery.ddd.Aggregate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Courier extends Aggregate<UUID> {

    public static final int MAX_STORAGE_CAPACITY = 10;

    private String name;
    private Integer speed;
    private Location location;
    private List<StoragePlace> storagePlaces = new ArrayList<>();

    private Courier() {

    }

    public Courier(String name, Integer speed, Location location) {
        super(UUID.randomUUID());

        if (name == null) {
            throw new IllegalArgumentException("Courier name cannot be null.");
        }

        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Courier name cannot be empty or blank.");
        }

        if (speed == null) {
            throw new IllegalArgumentException("Courier speed cannot be null.");
        }

        if (speed < 0) {
            throw new IllegalArgumentException("Courier speed cannot be negative.");
        }

        if (location == null) {
            throw new IllegalArgumentException("Courier location cannot be null.");
        }

        StoragePlace defaultStoragePlace = new StoragePlace("Сумка", 10);

        this.storagePlaces.add(defaultStoragePlace);
        this.name = name;
        this.speed = speed;
        this.location = location;
    }

    private Courier(UUID id, String name, Integer speed, Location location, List<StoragePlace> storagePlaces) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.location = location;
        this.storagePlaces = storagePlaces;
    }

    public void addStoragePlace(String name, Integer volume) {
        StoragePlace newStoragePlace = new StoragePlace(name, volume);
        this.storagePlaces.add(newStoragePlace);
    }

    public Boolean canTakeOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Courier order cannot be null.");
        }

        Integer volumeOrder = order.getVolume();

        for (StoragePlace storagePlace : storagePlaces) {
            if (storagePlace.canStore(volumeOrder)) {
                return true;
            }
        }

        return false;
    }

    public void takeOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Courier order cannot be null.");
        }

        Boolean hasStorageSpace = canTakeOrder(order);

        if (hasStorageSpace) {
            Integer volumeOrder = order.getVolume();

            for (StoragePlace storagePlace : this.storagePlaces) {
                if (storagePlace.canStore(volumeOrder)) {
                    storagePlace.store(order.getId(), volumeOrder);
                    return;
                }
            }

        }
    }

    public void completedOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Courier order cannot be null.");
        }

        for (StoragePlace storagePlace : this.storagePlaces) {
            if (order.getId().equals(storagePlace.getOrderId())) {
                storagePlace.clear(order.getId());
            }
        }
    }

    public Double calculateTimeToLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }

        int distance = this.location.distanceTo(location);

        return (double) distance / this.speed;
    }

    public void move(Location target) {
        if (target == null) {
            throw new IllegalArgumentException("Target order cannot be null.");
        }

        int difX = target.getX() - this.location.getX();
        int difY = target.getY() - this.location.getY();
        int cruisingRange = speed;

        int moveX = Math.max(-cruisingRange, Math.min(difX, cruisingRange));
        cruisingRange -= Math.abs(moveX);

        int moveY = Math.max(-cruisingRange, Math.min(difY, cruisingRange));

        Location location = new Location(this.location.getX() + moveX, this.location.getY() + moveY);

        this.location = location;
    }

    public static Courier reStore(UUID id, String name, Integer speed, Location location,
            List<StoragePlace> storagePlaces) {
        return new Courier(id, name, speed, location, storagePlaces);
    }
}
