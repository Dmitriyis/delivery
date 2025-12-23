package com.delivery;

import com.delivery.ddd.Aggregate;
import com.delivery.ddd.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    public void publish(Iterable<Aggregate<?>> aggregates);
}
