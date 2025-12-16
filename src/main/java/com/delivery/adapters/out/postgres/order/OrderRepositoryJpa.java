package com.delivery.adapters.out.postgres.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepositoryJpa extends JpaRepository<OrderDataModelEntity, UUID> {

    @Query(value = "select * from orders o where o.status = 'CREATED' LIMIT 1", nativeQuery = true)
    Optional<OrderDataModelEntity> findRandomOneOrderWithStatusCreated();

    @Query(value = "select * from orders o where o.status = 'ASSIGNED'", nativeQuery = true)
    List<OrderDataModelEntity> findAllOrderWithStatusAssigned();
}
