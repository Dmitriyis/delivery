package com.delivery.core.application.commands;

import lombok.Getter;

import java.util.UUID;

@Getter
public final class AddStoragePlaceCommand {
    private UUID courierId;
    private String name;
    private Integer totalVolume;

    public AddStoragePlaceCommand(UUID courierId, String name, Integer totalVolume) {
        if (courierId == null) {
            throw new IllegalArgumentException("Courier ID cannot be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("Storage place name cannot be null");
        }

        if (totalVolume == null) {
            throw new IllegalArgumentException("Total volume cannot be null");
        }

        this.courierId = courierId;
        this.name = name;
        this.totalVolume = totalVolume;
    }
}
