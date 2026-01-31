package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Long id;
    private String status;

    @JsonProperty("picked_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime pickedAt;

    @JsonProperty("delivered_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime deliveredAt;

    @JsonProperty("tracking_url")
    private String trackingUrl;

    @JsonProperty("client_reference")
    private String clientReference;

    @JsonProperty("package_description")
    private String packageDescription;

    @JsonProperty("package_type")
    private String packageType;

    @JsonProperty("fleet_ids")
    private List<Integer> fleetIds;

    private LocationDto pickup;
    private LocationDto dropoff;
    private CancellationDto cancellation;
    private EtaDto eta;
    private ProofDto proof;

    @JsonProperty("package_image_url")
    private String packageImageUrl;

    @JsonProperty("client_tracking_url")
    private String clientTrackingUrl;

    @JsonProperty("proof_of_delivery_url")
    private String proofOfDeliveryUrl;

    public String getDropoffAddress() {
        return this.getDropoff().getAddress();
    }

    public String getPickupAddress() {
        return this.pickup.getAddress();
    }

    public String getPickupContactName() {
        return this.pickup.getContactName();
    }

    public String getDropoffContactName() {
        return this.dropoff.getContactName();
    }

    public String getPickupContactNumber() {
        return this.pickup.getContactNumber();
    }

    public String getDropoffContactNumber() {
        return this.dropoff.getContactNumber();
    }
}