package com.delivery.core.application.commands;

import com.delivery.DomainEventPublisher;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.domain.model.order.Order;
import com.delivery.core.ports.GeoClient;
import com.delivery.core.ports.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrderCommandHandlerImpl implements CreateOrderCommandHandler {

    private final GeoClient geoClient;
    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public void createOrder(CreateOrderCommand createOrderCommand) {
        Location location = geoClient.getGeolocation(createOrderCommand.getStreet());

        Order order = new Order(createOrderCommand.getId(), location, createOrderCommand.getVolume());

        orderRepository.addOrder(order);
        domainEventPublisher.publish(order.getDomainEvents());
    }
}