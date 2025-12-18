package com.delivery.core.application.commands;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.order.Order;
import com.delivery.core.domain.services.OrderDispatcher;
import com.delivery.core.ports.CourierRepository;
import com.delivery.core.ports.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignOrderToCourierCommandHandlerImpl implements AssignOrderToCourierCommandHandler {

    private final OrderDispatcher orderDispatcher;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;


    @Override
    @Transactional
    public void assignOrderToCourier() {
        List<Courier> couriers = courierRepository.getAllFreeCouriersWhereAllStorageSpacesAvailable();

        Order order = orderRepository.getRandomOneOrderWithStatusCreated();

        Courier courierWin = orderDispatcher.dispatch(order, couriers);

        courierWin.takeOrder(order);
        order.assigned(courierWin);

        courierRepository.updateCourier(courierWin);
        orderRepository.updateOrder(order);
    }
}
