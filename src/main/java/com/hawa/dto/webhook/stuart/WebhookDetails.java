package com.hawa.dto.webhook.stuart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WebhookDetails {

    private Account account;

    @JsonProperty("package")
    private Package pkg;  // Note: 'package' is a reserved word

    private Courier courier;

    private String task;

    @JsonProperty("proximity_radius")
    private ProximityRadius proximityRadius;

    @JsonProperty("new_package")
    private NewPackage newPackage;

    private Cancellation cancellation;

    @JsonProperty("return")
    private Return ret;

    private Reassigning reassigning;

    private Coordinates coordinates;

    public String getPackageId(){
        return this.pkg.getId();
    }

}
