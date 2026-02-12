package com.hawa.service;

import com.hawa.dto.webhook.stuart.WebhookDto;
import com.hawa.model.Delivery;

public interface WebhookService {

    void updateDeliveryStatus(WebhookDto webhookDto);
}
