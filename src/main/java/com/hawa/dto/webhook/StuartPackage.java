package com.hawa.dto.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StuartPackage {

    @JsonProperty("id")
    private String id;  // "10000000001"

    @JsonProperty("reference")
    private String reference;  // "1676298576"

    @JsonProperty("client_tracking_url")
    private String clientTrackingUrl;  // "https://stuart.sandbox..."

    @JsonProperty("end_customer_tracking_url")
    private String endCustomerTrackingUrl;  // "https://stuart.sandbox..."
}