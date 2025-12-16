package com.delivery.core.domain.model.courier;

import com.delivery.ddd.BaseEntity;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StoragePlace extends BaseEntity<UUID> {

    private String name;
    private Integer totalVolume;
    private UUID orderId;

    private StoragePlace() {

    }

    public StoragePlace(String name, Integer totalValue) {
        super(UUID.randomUUID());

        if (name == null) {
            throw new IllegalArgumentException("Name is required for storage.");
        }

        if (totalValue == null) {
            throw new IllegalArgumentException("TotalValue is required for storage.");
        }

        if (totalValue < 1) {
            throw new IllegalArgumentException("Storage place capacity must be positive, got: " + totalValue + ".");
        }

        this.name = name;
        this.totalVolume = totalValue;
    }

    private StoragePlace(UUID id, String name, Integer totalValue, UUID orderId) {
        this.id = id;
        this.name = name;
        this.totalVolume = totalValue;
        this.orderId = orderId;
    }

    public boolean canStore(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is required when checking the availability store.");
        }

        if (this.orderId != null) {
            return false;
        }

        if (value > this.totalVolume) {
            return false;
        }

        return true;
    }

    public void store(UUID orderId, Integer value) {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId is required for storage.");
        }

        if (value == null) {
            throw new IllegalArgumentException("Value is required when placing order.");
        }

        if (value < 1) {
            throw new IllegalArgumentException("Product size must be positive.");
        }

        if (!canStore(value)) {
            throw new IllegalArgumentException("Order size " + value + " exceeds maximum volume " + totalVolume);
        }

        if (this.orderId != null) {
            throw new IllegalArgumentException("Storage place is already occupied.");
        }

        this.orderId = orderId;
    }

    public void clear(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId is required when clearing order.");
        }

        if (!orderId.equals(this.orderId)) {
            throw new IllegalArgumentException("OrderId mismatch.");
        }

        this.orderId = null;
    }

    public boolean isOccupied() {
        return this.orderId != null;
    }

    public static StoragePlace reStore(UUID id, String name, Integer totalValue, UUID orderId) {
        return new StoragePlace(id, name, totalValue, orderId);
    }
}
