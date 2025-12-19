package com.delivery.core.application.queries;

import com.delivery.core.application.queries.GetAllUnfinishedOrdersResponse.LocationOrderResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllUnfinishedOrdersHandlerImpl implements GetAllUnfinishedOrdersHandler {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<GetAllUnfinishedOrdersResponse> getAllUnfinishedOrders() {

        List<Object[]> rawResults = entityManager
                .createNativeQuery("select o.volume, o.status, o.x, o.y, o.id, c.id from orders o "
                        + "left join couriers c on c.id = o.courier_id "
                        + "where status = 'CREATED' or status = 'ASSIGNED'")
                .getResultList();

        return rawResults.stream().map(row -> {
            GetAllUnfinishedOrdersResponse allUnfinishedOrdersResponse = new GetAllUnfinishedOrdersResponse();

            allUnfinishedOrdersResponse.setVolume((Integer) row[0]);
            allUnfinishedOrdersResponse.setOrderStatus((String) row[1]);
            allUnfinishedOrdersResponse.setId((UUID) row[4]);
            allUnfinishedOrdersResponse.setCourierId((UUID) row[5]);

            LocationOrderResponse locationOrderResponse = new LocationOrderResponse();
            locationOrderResponse.setX((Integer) row[2]);
            locationOrderResponse.setY((Integer) row[3]);
            allUnfinishedOrdersResponse.setLocation(locationOrderResponse);

            return allUnfinishedOrdersResponse;
        }).collect(Collectors.toList());
    }
}
