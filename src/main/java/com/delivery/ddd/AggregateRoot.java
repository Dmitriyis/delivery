package com.delivery.ddd;

import java.util.List;

public interface AggregateRoot<ID> {
    ID getId();
    List<DomainEvent> getDomainEvents();
    void clearDomainEvents();
}

