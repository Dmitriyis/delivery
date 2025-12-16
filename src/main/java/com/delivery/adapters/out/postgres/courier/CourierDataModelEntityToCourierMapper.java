package com.delivery.adapters.out.postgres.courier;

import com.delivery.core.domain.model.courier.Courier;
import com.delivery.core.domain.model.courier.StoragePlace;

import java.util.List;

public class CourierDataModelEntityToCourierMapper {
    public static Courier toDomain(CourierDataModelEntity courierDataModelEntity) {
        List<StoragePlace> storagePlacesDomain = StoragePlaceDataModelEntityToStoragePlaceMapper.toDomain(courierDataModelEntity.getStoragePlaces());

        return Courier.reStore(courierDataModelEntity.getId(), courierDataModelEntity.getName(), courierDataModelEntity.getSpeed(), courierDataModelEntity.getLocation(), storagePlacesDomain);
    }


}
