package com.delivery.adapters.out.postgres.courier;

import com.delivery.core.domain.model.courier.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourierRepositoryJpa extends JpaRepository<CourierDataModelEntity, UUID> {

    @Query("select c from CourierDataModelEntity c where" +
            " not exists (select 1 from StoragePlaceDataModelEntity sp where sp.courier = c and sp.order is not null)")
    List<CourierDataModelEntity> findAllFreeCouriersWhereAllStorageSpacesAvailable();

    @Query("select distinct c from CourierDataModelEntity c " +
            "inner join StoragePlaceDataModelEntity sp on sp.courier = c " +
            "where sp.order is not null")
    List<CourierDataModelEntity> getAllWithOrders();
}
