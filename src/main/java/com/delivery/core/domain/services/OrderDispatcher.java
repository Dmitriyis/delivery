package com.delivery.core.domain.services;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.order.Order;

import java.util.List;

public interface OrderDispatcher {
    Courier dispatch(Order order, List<Courier> couriers);
}
