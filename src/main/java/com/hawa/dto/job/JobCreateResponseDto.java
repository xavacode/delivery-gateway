package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class JobCreateResponseDto {

    private Long id;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime createdAt;

    private String status;

    @JsonProperty("package_type")
    private String packageType;

    @JsonProperty("transport_type")
    private String transportType;

    @JsonProperty("assignment_code")
    private String assignmentCode;

    @JsonProperty("pickup_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime pickupAt;

    @JsonProperty("dropoff_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime dropoffAt;

    @JsonProperty("ended_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime endedAt;

    private String comment;
    private Double distance;
    private Integer duration;

    @JsonProperty("traveled_time")
    private Integer traveledTime;

    @JsonProperty("traveled_distance")
    private Double traveledDistance;

    private List<DeliveryDto> deliveries;
    private DriverDto driver;
    private PricingDto pricing;
    private RatingDto rating;

}
