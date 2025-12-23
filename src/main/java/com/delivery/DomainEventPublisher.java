package com.delivery;

import com.delivery.ddd.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DomainEventPublisher{

    private final ApplicationEventPublisher publisher;

    public void publish(List<DomainEvent> events) {
        events.forEach(publisher::publishEvent);
    }
}