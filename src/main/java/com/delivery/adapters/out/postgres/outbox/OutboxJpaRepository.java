package com.delivery.adapters.out.postgres.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxJpaRepository extends JpaRepository<OutboxMessage, UUID> {

    @Query(value =
            "select " +
                    "id," +
                    " event_type," +
                    " aggregate_id," +
                    " aggregate_type," +
                    " payload," +
                    " occurred_on_utc," +
                    " processed_on_utc" +
                    " from outbox " +
                    "where processed_on_utc is null", nativeQuery = true)
    List<OutboxMessage> findUnprocessedMessages();
}
