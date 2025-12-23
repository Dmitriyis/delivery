package com.delivery.core.ports;

import com.delivery.core.domain.model.order.events.OrderCompletedDomainEvent;
import com.delivery.core.domain.model.order.events.OrderCreatedDomainEvent;

public interface OrdersEventsProducer {
    void publish(OrderCompletedDomainEvent domainEvent) throws Exception;
    void publish(OrderCreatedDomainEvent domainEvent) throws Exception;
}
