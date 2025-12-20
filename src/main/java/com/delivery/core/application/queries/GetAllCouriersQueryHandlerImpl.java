package com.delivery.core.application.queries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllCouriersQueryHandlerImpl implements GetAllCouriersQueryHandler {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<GetAllCouriersResponse> getAllCouriers() {
        List<Object[]> rawResults = entityManager.createNativeQuery("select id, name, x, y from couriers").getResultList();

        return rawResults.stream().map(row -> {
            GetAllCouriersResponse response = new GetAllCouriersResponse();
            response.setId((UUID) row[0]);
            response.setName((String) row[1]);

            GetAllCouriersResponse.LocationCourierResponse location = new GetAllCouriersResponse.LocationCourierResponse();
            location.setX((Integer) row[2]);
            location.setY((Integer) row[3]);

            response.setLocation(location);

            return response;
        }).collect(Collectors.toList());
    }
}
