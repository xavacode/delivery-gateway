package com.hawa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawa.config.StuartApiConfig;
import com.hawa.constant.ErrorCodeConstant;
import com.hawa.dto.job.*;
import com.hawa.exception.NotFoundException;
import com.hawa.model.Delivery;
import com.hawa.model.Job;
import com.hawa.model.Store;
import com.hawa.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class StuartServiceImpl implements StuartService {

    private final RestTemplate restTemplate;
    private final JobRepository jobRepository;
    private final ObjectMapper objectMapper;
    private final StuartApiConfig stuartApiConfig;
    @Value("${app.delivery-charge.added-amount}")
    private BigDecimal addedAmount;

    @Override
    @Transactional
    public JobCreateResponseDto createJob(JobCreateRequestDto jobCreateRequestDto) {
        HttpEntity<JobCreateRequestDto> request = new HttpEntity<>(jobCreateRequestDto);
        ResponseEntity<JobCreateResponseDto> response = restTemplate.exchange(
                stuartApiConfig.getJobCreateUrl(),
                HttpMethod.POST,
                request,
                JobCreateResponseDto.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            try {
                JobCreateResponseDto jobCreateResponseDto = response.getBody();
                Job job = new Job();
                job.setId(jobCreateResponseDto.getId());
                job.setStatus(jobCreateResponseDto.getStatus());
                job.setCreatedAt(jobCreateResponseDto.getCreatedAt());
                job.setDropoffAt(jobCreateResponseDto.getDropoffAt());
                job.setEndedAt(jobCreateResponseDto.getEndedAt());
                job.setPickupAt(jobCreateResponseDto.getPickupAt());
                job.setPackageType(jobCreateResponseDto.getPackageType());
                job.setPrice(jobCreateResponseDto.getPricing().getPriceTaxIncluded().add(addedAmount));
                Store store=new Store();
                store.setId(jobCreateRequestDto.getStoreId());
                job.setStore(store);
                try {
                    job.setResponsePayload(objectMapper.writeValueAsString(jobCreateResponseDto));
                } catch (JsonProcessingException e) {
                    job.setResponsePayload(null);
                }
                try {
                    job.setRequestPayload(objectMapper.writeValueAsString(jobCreateRequestDto));
                } catch (JsonProcessingException e) {
                    job.setRequestPayload(null);
                }
                for (DeliveryDto deliveryDto : jobCreateResponseDto.getDeliveries()) {
                    Delivery delivery = new Delivery();
                    delivery.setId(deliveryDto.getId());
                    delivery.setClientReference(deliveryDto.getClientReference());
                    delivery.setDropoffAddress(deliveryDto.getDropoffAddress());
                    delivery.setPickupAddress(deliveryDto.getPickupAddress());
                    delivery.setStatus(deliveryDto.getStatus());
                    delivery.setClientTrackingUrl(deliveryDto.getClientTrackingUrl());
                    delivery.setDropoffContactName(deliveryDto.getDropoffContactName());
                    delivery.setPickupContactName(deliveryDto.getPickupContactName());
                    delivery.setDropoffContactNumber(deliveryDto.getDropoffContactNumber());
                    delivery.setPickupContactNumber(delivery.getPickupContactNumber());
                    job.addDelivery(delivery);
                }
                jobRepository.save(job);
            } catch (Exception e) {
                log.error("Error saving Job: ", e);
            }
            return response.getBody();
        }
        throw new RuntimeException("Failed to Create Job: " + response.getStatusCode());
    }


    @Override
    @Cacheable(
            value = "job-validates",
            keyGenerator = "payloadKeyGenerator",
            unless = "#result == null"
    )
    public JobValidationResponseDto validateJob(JobCreateRequestDto jobCreateRequestDto) {
        HttpEntity<JobCreateRequestDto> request = new HttpEntity<>(jobCreateRequestDto);
        ResponseEntity<JobValidationResponseDto> response = restTemplate.exchange(
                stuartApiConfig.getJobValidateUrl(),
                HttpMethod.POST,
                request,
                JobValidationResponseDto.class
        );
        log.info("Response fetched from api");
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to Validate Job: " + response.getStatusCode());
    }

    @Override
    @Cacheable(
            value = "job-pricings",
            keyGenerator = "payloadKeyGenerator",
            unless = "#result == null"
    )
    public JobPricingResponseDto getJobPrice(JobCreateRequestDto jobCreateRequestDto) {
        HttpEntity<JobCreateRequestDto> request = new HttpEntity<>(jobCreateRequestDto);
        ResponseEntity<JobPricingResponseDto> response = restTemplate.exchange(
                stuartApiConfig.getJobPricingUrl(),
                HttpMethod.POST,
                request,
                JobPricingResponseDto.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to Get Job Price: " + response.getStatusCode());
    }

    @Override
    public JobPickupEtaResponseDto getJobPickupEta(JobCreateRequestDto jobCreateRequestDto) {
        HttpEntity<JobCreateRequestDto> request = new HttpEntity<>(jobCreateRequestDto);
        ResponseEntity<JobPickupEtaResponseDto> response = restTemplate.exchange(
                stuartApiConfig.getJobPickupEtaUrl(),
                HttpMethod.POST,
                request,
                JobPickupEtaResponseDto.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to Get Job Pickup ETA: " + response.getStatusCode());
    }

    @Override
    @Cacheable(
            value = "job-drop-off-etas",
            keyGenerator = "payloadKeyGenerator",
            unless = "#result == null"
    )
    public JobDropoffEtaResponseDto getJobDropoffEta(JobCreateRequestDto jobCreateRequestDto) {
        HttpEntity<JobCreateRequestDto> request = new HttpEntity<>(jobCreateRequestDto);
        ResponseEntity<JobDropoffEtaResponseDto> response = restTemplate.exchange(
                stuartApiConfig.getJobDropoffEtaUrl(),
                HttpMethod.POST,
                request,
                JobDropoffEtaResponseDto.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to Get Job Dropoff ETA: " + response.getStatusCode());
    }

    @Override
    @Transactional
    public void cancelJob(long jobId, JobCancellationRequestDto jobCancellationRequestDto) {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new NotFoundException(String.format(ErrorCodeConstant.NOT_FOUND.getMessage(), "JobId"))
        );
        ResponseEntity<Void> response = null;
        if (jobCancellationRequestDto != null) {
            HttpEntity<JobCancellationRequestDto> request = new HttpEntity<>(jobCancellationRequestDto);
            response = restTemplate.exchange(
                    stuartApiConfig.getJobCancellationUrl(jobId),
                    HttpMethod.POST,
                    request,
                    Void.class
            );
        } else {
            response = restTemplate.exchange(
                    stuartApiConfig.getJobCancellationUrl(jobId),
                    HttpMethod.POST,
                    null,
                    Void.class
            );
        }
        if (response.getStatusCode().is2xxSuccessful()) {
            job.setStatus("CANCELLED");
            return;
        }
        throw new RuntimeException("Failed to Cancel Job : " + response.getStatusCode());
    }

    @Override
    public JobCreateResponseDto getJob(long jobId) {
        ResponseEntity<JobCreateResponseDto> response = restTemplate.exchange(
                stuartApiConfig.getJobReadUrl(jobId),
                HttpMethod.GET,
                null,
                JobCreateResponseDto.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Failed to Get Job : " + response.getStatusCode());

    }
}
