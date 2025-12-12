package com.delivery.core.ports;

import com.delivery.core.domain.model.courier.Courier;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public interface CourierRepository {
    void addCourier(Courier courier);
    void updateCourier(Courier courier);

    Courier getById(UUID courierId) throws Exception;

    List<Courier> getAllFreeCouriersWhereAllStorageSpacesAvailable() throws Exception;
}
