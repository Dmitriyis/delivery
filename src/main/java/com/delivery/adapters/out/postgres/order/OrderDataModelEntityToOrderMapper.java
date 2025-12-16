package com.delivery.adapters.out.postgres.order;

import com.delivery.core.domain.model.order.Order;

import java.util.UUID;

public class OrderDataModelEntityToOrderMapper {
    public static Order toDomain(OrderDataModelEntity orderDataModelEntity) {
        UUID courierId = null;
        if (orderDataModelEntity.getCourier() != null) {
            courierId = orderDataModelEntity.getCourier().getId();
        }

        return Order.reStore(orderDataModelEntity.getId(), orderDataModelEntity.getLocation(), orderDataModelEntity.getVolume(), orderDataModelEntity.getOrderStatus(), courierId);
    }
}
