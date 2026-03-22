package com.pratik.microservices.order_system.orders.repository;

import com.pratik.microservices.order_system.orders.entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEventEntity, Long> {

    @Query(value = """
        SELECT * FROM outbox_event_entity
        WHERE status = 'NEW'
        ORDER BY id
        LIMIT :limit
        FOR UPDATE SKIP LOCKED
    """, nativeQuery = true)
    List<OutboxEventEntity> fetchBatchForUpdate(@Param("limit") int limit);
}
