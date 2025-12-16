package com.delivery.adapters.out.postgres.order;

import com.delivery.adapters.out.postgres.courier.CourierDataModelEntity;
import com.delivery.core.domain.model.order.Order;
import com.delivery.core.ports.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderRepositoryJpa orderRepositoryJpa;

    @Override
    public void addOrder(Order order) {
        OrderDataModelEntity orderDataModelRoot = orderToOrderDataModelRoot(order);
        orderRepositoryJpa.save(orderDataModelRoot);
    }

    @Override
    public void updateOrder(Order order) {
        OrderDataModelEntity orderDataModelRoot = orderToOrderDataModelRoot(order);
        orderRepositoryJpa.save(orderDataModelRoot);
    }

    @Override
    public Order getById(UUID orderId) {
        OrderDataModelEntity orderDataModelEntity = orderRepositoryJpa.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Not found OrderDataModelEntity"));
        return OrderDataModelEntityToOrderMapper.toDomain(orderDataModelEntity);
    }

    @Override
    public Order getRandomOneOrderWithStatusCreated() {
        OrderDataModelEntity randomOneOrderWithStatusCreated = orderRepositoryJpa.findRandomOneOrderWithStatusCreated()
                .orElseThrow(() -> new NoSuchElementException("Not found OrderDataModelEntity"));
        return OrderDataModelEntityToOrderMapper.toDomain(randomOneOrderWithStatusCreated);
    }

    @Override
    public List<Order> getAllOrderWithStatusAssigned() {
        return orderRepositoryJpa.findAllOrderWithStatusAssigned()
                .stream()
                .map(OrderDataModelEntityToOrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    public OrderDataModelEntity orderToOrderDataModelRoot(Order order) {
        CourierDataModelEntity courierDataModelRoot = null;
        if (order.getCourierId() != null) {
            courierDataModelRoot = CourierDataModelEntity
                    .builder()
                    .id(order.getCourierId())
                    .build();
        }

        return OrderDataModelEntity
                .builder()
                .id(order.getId())
                .location(order.getLocation())
                .volume(order.getVolume())
                .orderStatus(order.getOrderStatus())
                .courier(courierDataModelRoot)
                .build();
    }
}
