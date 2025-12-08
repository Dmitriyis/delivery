package com.delivery.core.domain.services;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.domain.model.order.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderDispatcherImplTest {

    @Test
    public void dispatch_FailTest() {
        // Arrange
        Location locationCourierFirst = new Location(1, 1);
        Courier courierFirst = new Courier("StFirst", 6, locationCourierFirst);

        Location locationCourierSecond  = new Location(4, 7);
        Courier courierSecond = new Courier("StSecond", 2, locationCourierSecond);

        Location locationCourierThird  = new Location(2, 5);
        Courier courierSecondThird = new Courier("StThird ", 4, locationCourierThird);

        // Win
        Location locationCourierForSkip = new Location(6, 1);
        Courier courierFirstForSkip = new Courier("StSkip", 6, locationCourierForSkip);

        Location locationOrderForSkip = new Location(4, 4);
        Order orderForSkip = new Order(UUID.randomUUID(), locationOrderForSkip, 5);

        orderForSkip.assigned(courierFirstForSkip);
        courierFirstForSkip.takeOrder(orderForSkip);

        Location locationOrder = new Location(7, 2);
        Order order = new Order(UUID.randomUUID(), locationOrder, 5);

        List<Courier> couriers = List.of(courierFirst, courierSecond, courierSecondThird, courierFirstForSkip);

        OrderDispatcherImpl orderDispatcher = new OrderDispatcherImpl();

        // Act
        // Assert
        assertThrows(IllegalStateException.class, () -> {
            orderDispatcher.dispatch(order, couriers);
        });
    }

    @Test
    public void dispatch_SuccessfulTest() {
        // Arrange
        Location locationCourierFirst = new Location(1, 1);
        Courier courierFirst = new Courier("StFirst", 6, locationCourierFirst);

        Location locationCourierSecond  = new Location(4, 7);
        Courier courierSecond = new Courier("StSecond", 2, locationCourierSecond);

        Location locationCourierThird  = new Location(6, 1);
        Courier courierSecondThird = new Courier("StThird ", 4, locationCourierThird);


        Location locationOrder = new Location(7, 2);
        Order order = new Order(UUID.randomUUID(), locationOrder, 5);

        List<Courier> couriers = List.of(courierFirst, courierSecond, courierSecondThird);

        OrderDispatcherImpl orderDispatcher = new OrderDispatcherImpl();

        // Act
        Courier courierWin = orderDispatcher.dispatch(order, couriers);

        // Assert
        assertEquals(courierWin.getId(), courierSecondThird.getId());
        assertEquals(courierWin.getStoragePlaces().get(0).getOrderId(), order.getId());
        assertEquals(courierWin.getId(), order.getCourierId());
    }
}