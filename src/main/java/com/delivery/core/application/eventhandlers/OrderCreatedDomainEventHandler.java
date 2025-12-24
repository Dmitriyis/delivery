package com.delivery.core.application.eventhandlers;

import com.delivery.core.domain.model.order.events.OrderCreatedDomainEvent;
import com.delivery.core.ports.OrdersEventsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCreatedDomainEventHandler {

    private final OrdersEventsProducer ordersEventsProducer;


    @EventListener
    public Boolean handle(OrderCreatedDomainEvent event) throws Exception {
        ordersEventsProducer.publish(event);
        return true;
    }
}
