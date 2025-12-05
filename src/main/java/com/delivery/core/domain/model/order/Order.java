package com.delivery.core.domain.model.order;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.ddd.Aggregate;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Order extends Aggregate<UUID> {

    private Location location;

    private Integer volume;

    private OrderStatus orderStatus;

    private UUID courierId;


    private Order() {
    }

    public Order(UUID id, Location location, Integer volume) {
        super(id);

        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }

        if (location == null) {
            throw new IllegalArgumentException("Order location cannot be null.");
        }

        if (volume == null) {
            throw new IllegalArgumentException("Order volume cannot be null.");
        }

        if (volume < 1) {
            throw new IllegalArgumentException("Order volume must be at least 1.");
        }

        this.location = location;
        this.volume = volume;
        this.orderStatus = OrderStatus.CREATED;
    }

    public void assigned(Courier courier) {
        if (courier == null) {
            throw new IllegalArgumentException("Courier cannot be null");
        }

        if (this.orderStatus == OrderStatus.ASSIGNED) {
            throw new IllegalStateException("Order is already assigned to a courier");
        }

        if (this.orderStatus == OrderStatus.COMPLETED) {
            throw new IllegalStateException ("Cannot assign a courier to a completed order");
        }

        this.courierId = courier.getId();
        this.orderStatus = OrderStatus.ASSIGNED;
    }

    public void completed() {
        if (this.orderStatus == OrderStatus.CREATED) {
            throw new IllegalStateException ("Cannot complete an order that hasn't been assigned to a courier");
        }

        if (this.orderStatus == OrderStatus.COMPLETED) {
            throw new IllegalStateException ("Order is already completed");
        }

        this.orderStatus = OrderStatus.COMPLETED;
    }
}
