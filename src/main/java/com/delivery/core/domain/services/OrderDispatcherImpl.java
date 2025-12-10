package com.delivery.core.domain.services;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.order.Order;
import com.delivery.core.domain.model.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDispatcherImpl implements OrderDispatcher {

    @Override
    public Courier dispatch(Order order, List<Courier> couriers) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }

        if (!order.getOrderStatus().equals(OrderStatus.CREATED)) {
            String messageEx = String.format("Order %s cannot be dispatched because it has status %s. Expected status: CREATED", order.getId(), order.getOrderStatus());

            throw new IllegalStateException(messageEx);
        }

        if (couriers == null || couriers.isEmpty()) {
            throw new IllegalArgumentException("Couriers list cannot be null or empty.");
        }

        Map<Double, Courier> courierDistance = new HashMap<>();

        couriers.forEach(courier -> {
            Double distance = courier.calculateTimeToLocation(order.getLocation());
            courierDistance.put(distance, courier);
        });

        Double minDistance = courierDistance
                .keySet()
                .stream()
                .mapToDouble(Double::valueOf)
                .min()
                .getAsDouble();

        Courier courier = courierDistance.get(minDistance);

        if (!courier.canTakeOrder(order)) {
            throw new IllegalStateException("Courier has no capacity for the assigned order. Update courier availability.");
        }

        order.assigned(courier);
        courier.takeOrder(order);

        return courier;
    }
}
