package com.delivery.adapters.out.postgres.outbox;


import com.delivery.ddd.DomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Job {

    private final OutboxJpaRepository jpa;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher publisher;

    @Scheduled(fixedDelay = 1000)
    public void run() {
        List<OutboxMessage> outboxMessages = jpa.findUnprocessedMessages();
        for (OutboxMessage outboxMessage : outboxMessages) {
            try {

                String eventClassName = outboxMessage.getEventType();
                Class<?> eventClass = Class.forName(eventClassName);
                Object eventObject = objectMapper.readValue(outboxMessage.getPayload(), eventClass);

                if (!(eventObject instanceof DomainEvent domainEvent)) {
                    throw new IllegalStateException("Invalid outbox message type: " + eventClass);
                }

                publisher.publishEvent(domainEvent);

                outboxMessage.markAsProcessed();
                jpa.save(outboxMessage);
            } catch (Exception e) {
                System.err.println("Failed to publish outbox message: " + e.getMessage());
            }
        }
    }
}
