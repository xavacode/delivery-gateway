package com.hawa.service;

import com.hawa.constant.ErrorCodeConstant;
import com.hawa.dto.webhook.stuart.WebhookDto;
import com.hawa.exception.NotFoundException;
import com.hawa.model.Delivery;
import com.hawa.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService{

    private final DeliveryRepository deliveryRepository;

    @Override
    @Transactional
    public void updateDeliveryStatus(WebhookDto webhookDto) {
        Delivery delivery=deliveryRepository.findById(webhookDto.getPackageId()).orElseThrow(
                () -> new NotFoundException(String.format(ErrorCodeConstant.NOT_FOUND.getMessage(), "JobId"))
        );
        delivery.setStatus(webhookDto.getTopic());
    }
}
