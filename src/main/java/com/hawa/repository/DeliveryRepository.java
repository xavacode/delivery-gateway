package com.hawa.repository;

import com.hawa.model.Delivery;
import com.hawa.model.Job;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @EntityGraph(attributePaths = {"deliveryLogs","job","job.store"})
    @Query("SELECT d FROM Delivery d WHERE d.id=:deliveryId")
    Delivery getDeliveryWithLogsAndJob(@Param("deliveryId") long deliveryId);
}
