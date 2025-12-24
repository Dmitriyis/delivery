package com.delivery.core.application.eventhandlers;

import com.delivery.core.domain.model.order.events.OrderCompletedDomainEvent;
import com.delivery.core.ports.OrdersEventsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCompletedDomainEventHandler {

    private final OrdersEventsProducer ordersEventsProducer;


    @EventListener
    public Boolean handle(OrderCompletedDomainEvent event) throws Exception {
        ordersEventsProducer.publish(event);
        return true;
    }
}
