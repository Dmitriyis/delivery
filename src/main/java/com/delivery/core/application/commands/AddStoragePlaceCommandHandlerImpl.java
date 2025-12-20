package com.delivery.core.application.commands;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.ports.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddStoragePlaceCommandHandlerImpl implements AddStoragePlaceCommandHandler {

    private final CourierRepository courierRepository;

    @Override
    @Transactional
    public void addStoragePlace(AddStoragePlaceCommand addStoragePlaceCommand) {
        Courier courier = courierRepository.getById(addStoragePlaceCommand.getCourierId());

        courier.addStoragePlace(addStoragePlaceCommand.getName(), addStoragePlaceCommand.getTotalVolume());

        courierRepository.updateCourier(courier);
    }
}
