package com.delivery.adapters.out.postgres.courier;

import com.delivery.core.domain.model.courier.StoragePlace;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StoragePlaceDataModelEntityToStoragePlaceMapper {
    public static List<StoragePlace> toDomain(List<StoragePlaceDataModelEntity> storagePlaceDataModelEntities) {

        return storagePlaceDataModelEntities
                .stream()
                .map(storagePlaceDataModelEntity -> {
                    UUID orderId = null;
                    if (storagePlaceDataModelEntity.getOrder() != null) {
                        orderId = storagePlaceDataModelEntity.getOrder().getId();
                    }

                    return StoragePlace.reStore(storagePlaceDataModelEntity.getId(), storagePlaceDataModelEntity.getName(), storagePlaceDataModelEntity.getTotalVolume(), orderId);
                })
                .collect(Collectors.toList());
    }
}
