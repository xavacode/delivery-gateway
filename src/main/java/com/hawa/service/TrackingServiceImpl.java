package com.hawa.service;

import com.hawa.model.Delivery;
import com.hawa.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService{

    private final DeliveryRepository deliveryRepository;

    @Override
    @Transactional(readOnly = true)
    public Delivery getDelivery(long deliveryId) {
        return deliveryRepository.getDeliveryWithLogsAndJob(deliveryId);
    }
}
