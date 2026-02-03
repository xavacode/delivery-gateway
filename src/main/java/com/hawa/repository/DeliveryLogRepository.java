package com.hawa.repository;

import com.hawa.model.DeliveryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryLogRepository extends JpaRepository<DeliveryLog, Long> {
}
