package com.delivery.core.domain.model.order.events;

import com.delivery.core.domain.model.order.Order;
import com.delivery.ddd.DomainEvent;
import lombok.Getter;

@Getter
public class OrderCreatedDomainEvent extends DomainEvent {
    private final String orderId;

    public OrderCreatedDomainEvent(Order order) {
        super(order);
        this.orderId = order.getId().toString();
    }
}
