package com.delivery.adapters;

import com.delivery.adapters.in.http.api.ApiApi;
import com.delivery.adapters.in.http.model.Courier;
import com.delivery.adapters.in.http.model.Location;
import com.delivery.adapters.in.http.model.NewCourier;
import com.delivery.adapters.in.http.model.Order;
import com.delivery.core.application.commands.CreateCourierCommand;
import com.delivery.core.application.commands.CreateCourierCommandHandler;
import com.delivery.core.application.commands.CreateOrderCommand;
import com.delivery.core.application.commands.CreateOrderCommandHandler;
import com.delivery.core.application.queries.GetAllCouriersQueryHandler;
import com.delivery.core.application.queries.GetAllUnfinishedOrdersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeliveryController implements ApiApi {

    private final CreateOrderCommandHandler createOrderCommandHandler;
    private final GetAllCouriersQueryHandler getAllCouriersQueryHandler;
    private final CreateCourierCommandHandler createCourierCommandHandler;
    private final GetAllUnfinishedOrdersHandler getAllUnfinishedOrdersHandler;

    @Override
    public ResponseEntity<Void> createCourier(NewCourier newCourier) {
        CreateCourierCommand createCourierCommand = new CreateCourierCommand(newCourier.getName(), newCourier.getSpeed());
        createCourierCommandHandler.createCourier(createCourierCommand);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createOrder() {
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(UUID.randomUUID(), "Несуществующая", 5);

        createOrderCommandHandler.createOrder(createOrderCommand);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Courier>> getCouriers() {
        List<Courier> couriers = getAllCouriersQueryHandler.getAllCouriers()
                .stream()
                .map(courier -> {
                    Location location = new Location(courier.getLocation().getX(), courier.getLocation().getY());
                    return new Courier(courier.getId(), courier.getName(), location);
                }).toList();
        return ResponseEntity.ok(couriers);
    }

    @Override
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = getAllUnfinishedOrdersHandler.getAllUnfinishedOrders()
                .stream()
                .map(order -> {
                    Location location = new Location(order.getLocation().getX(), order.getLocation().getY());
                    return new Order(order.getId(), location);
                }).toList();
        return ResponseEntity.ok(orders);
    }
}
