package com.delivery.adapters.in.kafka;


import com.delivery.core.application.commands.CreateOrderCommand;
import com.delivery.core.application.commands.CreateOrderCommandHandler;
import com.google.protobuf.util.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import queues.basket.BasketEventsProto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersEventsConsumer {

    private final CreateOrderCommandHandler createOrderCommandHandler;

    @KafkaListener(topics = "${app.kafka.baskets-events-topic}")
    public void createOrder(String message) {
        try {
            BasketEventsProto.BasketConfirmedIntegrationEvent.Builder builder = BasketEventsProto.BasketConfirmedIntegrationEvent.newBuilder();
            JsonFormat.parser().merge(message, builder);
            BasketEventsProto.BasketConfirmedIntegrationEvent event = builder.build();

            CreateOrderCommand createOrderCommand = new CreateOrderCommand(UUID.fromString(event.getBasketId()), event.getAddress().getStreet(), event.getVolume());

            createOrderCommandHandler.createOrder(createOrderCommand);

        } catch (com.google.protobuf.InvalidProtocolBufferException ex) {
            throw new RuntimeException("Failed to parse protobuf message", ex);
        }
    }
}
