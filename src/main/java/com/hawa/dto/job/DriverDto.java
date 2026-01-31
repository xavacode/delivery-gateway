package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DriverDto {

    private Long id;
    @JsonProperty("display_name")
    private String name;
    private String phone;
    @JsonProperty("transport_type")
    private String transportType;
    @JsonProperty("picture_url")
    private String pictureUrl;
    private double latitude;
    private double longitude;

}
