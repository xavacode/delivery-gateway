package com.hawa.service;

import com.hawa.constant.ErrorCodeConstant;
import com.hawa.dto.job.JobCreateResponseDto;
import com.hawa.dto.webhook.stuart.WebhookDto;
import com.hawa.exception.NotFoundException;
import com.hawa.model.Delivery;
import com.hawa.model.DeliveryLog;
import com.hawa.repository.DeliveryLogRepository;
import com.hawa.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryLogRepository deliveryLogRepository;
    private final StuartService stuartService;

    @Override
    @Transactional
    public void updateDeliveryStatus(WebhookDto webhookDto) {
        Delivery delivery = deliveryRepository.findById(webhookDto.getPackageId()).orElseThrow(
                () -> new NotFoundException(String.format(ErrorCodeConstant.NOT_FOUND.getMessage(), "JobId"))
        );
        if (webhookDto.getTopic().equals("courier_assigned")) {
            Hibernate.initialize(delivery.getJob());
            JobCreateResponseDto jobCreateResponseDto = stuartService.getJob(delivery.getJob().getId());
            delivery.setDropOffEta(getDropOffEta(jobCreateResponseDto));
            delivery.setCourierName(webhookDto.getDetails().getCourier().getName());
        }
        delivery.setStatus(webhookDto.getTopic());
        DeliveryLog deliveryLog = new DeliveryLog();
        deliveryLog.setDelivery(delivery);
        deliveryLog.setStatus(webhookDto.getTopic());
        deliveryLog.setCreatedAt(LocalDateTime.now());
        deliveryLogRepository.save(deliveryLog);
    }

    private OffsetDateTime getDropOffEta(JobCreateResponseDto jobCreateResponseDto) {
        if (jobCreateResponseDto.getDeliveries().get(0).getEta() != null) {
            return jobCreateResponseDto.getDeliveries().get(0).getEta().getDropoff();
        }
        return null;
    }
}
