package com.delivery.core.application.commands;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.ports.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCourierCommandHandlerImpl implements CreateCourierCommandHandler {

    private final CourierRepository courierRepository;

    @Override
    @Transactional
    public void createCourier(CreateCourierCommand createCourierCommand) {
        Location location = new Location(1, 1);
        Courier courier = new Courier(createCourierCommand.getName(), createCourierCommand.getSpeed(), location);

        courierRepository.addCourier(courier);
    }
}
