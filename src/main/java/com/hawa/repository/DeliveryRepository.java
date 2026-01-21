package com.hawa.repository;

import com.hawa.model.Delivery;
import com.hawa.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
