package com.hawa.service;

import com.hawa.dto.webhook.stuart.WebhookDto;

public interface WebhookService {

    void updateDeliveryStatus(WebhookDto webhookDto);
}
