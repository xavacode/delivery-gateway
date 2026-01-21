package com.hawa.service;

import com.hawa.dto.job.*;

import java.util.Map;

public interface StuartService {

    JobCreateResponseDto createJob(JobCreateRequestDto jobCreateRequestDto);
    JobValidationResponseDto validateJob(JobCreateRequestDto jobCreateRequestDto);
    JobPricingResponseDto getJobPrice(JobCreateRequestDto jobCreateRequestDto);
    JobPickupEtaResponseDto getJobPickupEta(JobCreateRequestDto jobCreateRequestDto);
    JobDropoffEtaResponseDto getJobDropoffEta(JobCreateRequestDto jobCreateRequestDto);
    void cancelJob(long jobId, JobCancellationRequestDto jobCancellationRequestDto);
    JobCreateResponseDto getJob(long jobId);
}
