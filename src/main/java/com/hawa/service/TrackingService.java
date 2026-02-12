package com.hawa.service;

import com.hawa.model.Delivery;

public interface TrackingService {
    Delivery getDelivery(long deliveryId);
}
