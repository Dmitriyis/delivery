package com.delivery.core.application.commands;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.domain.model.order.Order;
import com.delivery.core.ports.CourierRepository;
import com.delivery.core.ports.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoveCourierCommandHandlerImpl implements MoveCourierCommandHandler {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    @Override
    @Transactional
    public void moveCourier() {
        List<Courier> couriersWithOrders = courierRepository.getAllWithOrders();

        couriersWithOrders.forEach(courier -> {
            courier.getStoragePlaces().forEach(storagePlace -> {
                Order order = orderRepository.getById(storagePlace.getId());
                Location locationOrder = order.getLocation();
                courier.move(locationOrder);

                if (courier.getLocation().equals(locationOrder)) {
                    order.completed();
                    courier.completedOrder(order);
                }
            });
        });

        courierRepository.saveAll(couriersWithOrders);
    }
}
