package com.delivery.core.application.eventhandlers;

import com.delivery.core.domain.model.order.events.OrderCreatedDomainEvent;

public interface OrderCreatedDomainEventHandler {
    Boolean handle(OrderCreatedDomainEvent event) throws Exception;
}
