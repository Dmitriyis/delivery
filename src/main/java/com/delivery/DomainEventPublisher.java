package com.delivery;

import com.delivery.ddd.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    public void publish(List<DomainEvent> events);
}
