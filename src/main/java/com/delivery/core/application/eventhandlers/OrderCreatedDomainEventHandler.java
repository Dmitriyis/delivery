package com.delivery.core.application.eventhandlers;

import com.delivery.core.domain.model.order.events.OrderCreatedDomainEvent;

public interface OrderCreatedDomainEventHandler {
    void handle(OrderCreatedDomainEvent event) throws Exception;
}
