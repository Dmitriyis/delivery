package com.delivery.core.application.queries;

import com.delivery.core.AbstractIntegrationTest;
import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.ports.CourierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetAllCouriersQueryHandlerImplTest extends AbstractIntegrationTest {

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private GetAllCouriersQueryHandler getAllCouriersQueryHandler;

    @Test
    public void getAllCouriers_successfulTest() {
        // Arrange
        Courier courier1 = new Courier("St", 2, new Location(1, 1));
        Courier courier2 = new Courier("St", 2, new Location(1, 1));

        courierRepository.addCourier(courier1);
        courierRepository.addCourier(courier2);

        // Act
        List<GetAllCouriersResponse> allCouriers = getAllCouriersQueryHandler.getAllCouriers();

        // Assert
        assertEquals(allCouriers.size(), 2);
    }
}