package com.delivery.adapters.out.postgres.courier;

import com.delivery.adapters.out.postgres.order.OrderDataModelEntity;
import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.ports.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CourierRepositoryImpl implements CourierRepository {

    private final CourierRepositoryJpa courierRepositoryJpa;

    @Override
    public void addCourier(Courier courier) {
        CourierDataModelEntity courierDataModelRoot = courierToCourierDataModelRoot(courier);
        courierRepositoryJpa.save(courierDataModelRoot);
    }

    @Override
    public void updateCourier(Courier courier) {
        CourierDataModelEntity courierDataModelRoot = courierToCourierDataModelRoot(courier);
        courierRepositoryJpa.save(courierDataModelRoot);
    }

    @Override
    public Courier getById(UUID courierId) {
        CourierDataModelEntity courierDataModelRoot = courierRepositoryJpa.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Not found CourierDataModelEntity"));

        return CourierDataModelEntityToCourierMapper.toDomain(courierDataModelRoot);
    }

    @Override
    public List<Courier> getAllFreeCouriersWhereAllStorageSpacesAvailable() {
        List<CourierDataModelEntity> allFreeCouriersWhereAllStorageSpacesAvailable = courierRepositoryJpa.findAllFreeCouriersWhereAllStorageSpacesAvailable();
        List<Courier> couriers = new ArrayList<>();

        for (CourierDataModelEntity courierDataModelRoot : allFreeCouriersWhereAllStorageSpacesAvailable) {
            Courier courier = CourierDataModelEntityToCourierMapper.toDomain(courierDataModelRoot);
            couriers.add(courier);
        }

        return couriers;
    }

    @Override
    public List<Courier> getAllWithOrders() {
        return courierRepositoryJpa.getAllWithOrders();
    }

    @Override
    public void saveAll(List<Courier> couriers) {
        List<CourierDataModelEntity> courierDataModelEntities = couriers.stream()
                .map(this::courierToCourierDataModelRoot)
                .toList();

        courierRepositoryJpa.saveAll(courierDataModelEntities);
    }

    public CourierDataModelEntity courierToCourierDataModelRoot(Courier courier) {
        CourierDataModelEntity courierDataModelRoot = CourierDataModelEntity
                .builder()
                .id(courier.getId())
                .name(courier.getName())
                .speed(courier.getSpeed())
                .location(courier.getLocation())
                .build();

        List<StoragePlaceDataModelEntity> storagePlaceDataModelEntities = courier.getStoragePlaces()
                .stream()
                .map(storagePlace -> {

                    OrderDataModelEntity orderDataModelRoot = null;
                    if (storagePlace.getOrderId() != null) {
                        UUID orderId = storagePlace.getOrderId();
                        orderDataModelRoot = OrderDataModelEntity
                                .builder()
                                .id(orderId)
                                .build();
                    }

                    return StoragePlaceDataModelEntity
                            .builder()
                            .id(storagePlace.getId())
                            .name(storagePlace.getName())
                            .totalVolume(storagePlace.getTotalVolume())
                            .courier(courierDataModelRoot)
                            .order(orderDataModelRoot)
                            .build();
                }).collect(Collectors.toList());

        courierDataModelRoot.setStoragePlaces(storagePlaceDataModelEntities);

        return courierDataModelRoot;
    }
}
