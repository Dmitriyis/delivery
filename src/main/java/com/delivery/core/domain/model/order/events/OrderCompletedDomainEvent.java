package com.delivery.core.domain.model.order.events;

import com.delivery.core.domain.model.order.Order;
import com.delivery.ddd.DomainEvent;
import lombok.Getter;

@Getter
public class OrderCompletedDomainEvent extends DomainEvent {
    private String orderId;
    private String courierId;

    public OrderCompletedDomainEvent(Order order) {
        super(order);
        this.orderId = order.getId().toString();
        this.courierId = order.getCourierId().toString();
    }
}
