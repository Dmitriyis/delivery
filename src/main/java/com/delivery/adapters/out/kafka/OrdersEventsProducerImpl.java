package com.delivery.adapters.out.kafka;

import com.delivery.core.domain.model.order.events.OrderCompletedDomainEvent;
import com.delivery.core.domain.model.order.events.OrderCreatedDomainEvent;
import com.delivery.core.ports.OrdersEventsProducer;
import com.google.protobuf.util.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import queues.order.OrderEventsProto;
import queues.order.OrderEventsProto.OrderCompletedIntegrationEvent;
import queues.order.OrderEventsProto.OrderCreatedIntegrationEvent;

@Service
@RequiredArgsConstructor
public class OrdersEventsProducerImpl implements OrdersEventsProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.orders-events-topic}")
    private String topic;

    @Override
    public void publish(OrderCompletedDomainEvent domainEvent) throws Exception {
        OrderCompletedIntegrationEvent integrationEvent = mapToProto(domainEvent);
        var integrationEventAsJson = JsonFormat.printer().includingDefaultValueFields().print(integrationEvent);

        kafkaTemplate.send(topic, domainEvent.getOrderId(), integrationEventAsJson).get();
    }

    @Override
    public void publish(OrderCreatedDomainEvent domainEvent) throws Exception {
        OrderCreatedIntegrationEvent integrationEvent = mapToProto(domainEvent);
        var integrationEventAsJson = JsonFormat.printer().includingDefaultValueFields().print(integrationEvent);

        kafkaTemplate.send(topic, domainEvent.getOrderId(), integrationEventAsJson).get();
    }

    private OrderCompletedIntegrationEvent mapToProto(OrderCompletedDomainEvent domainEvent) {
        return OrderEventsProto.OrderCompletedIntegrationEvent
                .newBuilder()
                .setEventType(domainEvent.getClass().getSimpleName())
                .setEventId(domainEvent.getEventId().toString())
                .setOrderId(domainEvent.getOrderId())
                .setCourierId(domainEvent.getCourierId())
                .build();
    }

    private OrderCreatedIntegrationEvent mapToProto(OrderCreatedDomainEvent domainEvent) {
        return OrderCreatedIntegrationEvent
                .newBuilder()
                .setEventType(domainEvent.getClass().getSimpleName())
                .setEventId(domainEvent.getEventId().toString())
                .setOrderId(domainEvent.getOrderId())
                .build();
    }
}
