package com.delivery.core.domain.model.order;

public enum OrderStatus {
    CREATED,
    ASSIGNED,
    COMPLETED;

    public static OrderStatus fromValue(String value) {
        return OrderStatus.valueOf(value);
    }

    public String toValue() {
        return name();
    }
}
