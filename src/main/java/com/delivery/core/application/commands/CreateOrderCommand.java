package com.delivery.core.application.commands;

import lombok.Getter;

import java.util.UUID;

@Getter
public final class CreateOrderCommand {
    private UUID id;
    private String street;
    private Integer volume;

    private CreateOrderCommand() {

    }

    public CreateOrderCommand(UUID orderId,  String street, Integer volume) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        if (street == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }

        if (volume == null) {
            throw new IllegalArgumentException("Volume cannot be null");
        }

        if (volume < 1) {
            throw new IllegalArgumentException("Volume must be greater than zero");
        }

        this.id = orderId;
        this.street = street;
        this.volume = volume;
    }
}
