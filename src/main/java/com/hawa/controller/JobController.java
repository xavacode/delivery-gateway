package com.hawa.controller;

import com.hawa.constant.StuartCancellationReasonConstant;
import com.hawa.dto.job.*;
import com.hawa.service.StuartService;
import com.hawa.util.DateTimeUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Tag(name = "Delivery Job")
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final int ADDED_PRICE=3;

    private final StuartService stuartService;

    @PostMapping
    public ResponseEntity<JobCreateResponseDto> createJob(@RequestBody JobCreateRequestDto jobCreateRequestDto) {
        return ResponseEntity.ok(stuartService.createJob(jobCreateRequestDto));
    }

    @PostMapping("/validate")
    public ResponseEntity<JobValidationResponseDto> validateJob(@RequestBody JobCreateRequestDto jobCreateRequestDto) {
        return ResponseEntity.ok(stuartService.validateJob(jobCreateRequestDto));
    }

    @PostMapping("/pricing")
    public ResponseEntity<JobPricingResponseDto> getJobPrice(@RequestBody JobCreateRequestDto jobCreateRequestDto) {
        JobDropoffEtaResponseDto jobDropoffEtaResponseDto=stuartService.getJobDropoffEta(jobCreateRequestDto);
        JobPricingResponseDto jobPricingResponseDto = stuartService.getJobPrice(jobCreateRequestDto);
        jobPricingResponseDto.setDropoffEta(DateTimeUtil.formatHumanReadable(jobDropoffEtaResponseDto.getDropoffEta()));
        jobPricingResponseDto.setAmountWithTax(jobPricingResponseDto.getAmountWithTax().add(new BigDecimal(ADDED_PRICE)));
        return ResponseEntity.ok(jobPricingResponseDto);
    }

    @PostMapping("/pickup-eta")
    public ResponseEntity<JobPickupEtaResponseDto> getJobPickupEta(@RequestBody JobCreateRequestDto jobCreateRequestDto) {
        return ResponseEntity.ok(stuartService.getJobPickupEta(jobCreateRequestDto));
    }

    @PostMapping("/dropoff-eta")
    public ResponseEntity<JobDropoffEtaResponseDto> getJobDropoffEta(@RequestBody JobCreateRequestDto jobCreateRequestDto) {
        return ResponseEntity.ok(stuartService.getJobDropoffEta(jobCreateRequestDto));
    }

    @PostMapping("/{jobId}/cancel")
    public ResponseEntity<Void> cancelJob(
            @PathVariable long jobId,
            @RequestBody(required = false) JobCancellationRequestDto jobCancellationRequestDto) {
        stuartService.cancelJob(jobId, jobCancellationRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{jobId}/cancellation-reasons")
    public List<JobCancellationReasonDto> getCancellationReasons(@PathVariable long jobId) {
        return Arrays.stream(StuartCancellationReasonConstant.values())
                .map(stuartCancellationReasonConstant -> new JobCancellationReasonDto(
                        stuartCancellationReasonConstant.getDisplayName(),
                        stuartCancellationReasonConstant.name().toLowerCase()
                )).toList();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobCreateResponseDto> getJob(@PathVariable long jobId) {
        return ResponseEntity.ok(stuartService.getJob(jobId));
    }
}
