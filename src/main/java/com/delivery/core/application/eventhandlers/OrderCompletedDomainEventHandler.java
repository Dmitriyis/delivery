package com.delivery.core.application.eventhandlers;

import com.delivery.core.domain.model.order.events.OrderCompletedDomainEvent;

public interface OrderCompletedDomainEventHandler {
    void handle(OrderCompletedDomainEvent event) throws Exception;
}

