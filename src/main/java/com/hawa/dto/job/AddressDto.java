package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String postcode;
    private String city;
    private String zone;
    private String country;

    @JsonProperty("formatted_address")
    private String formattedAddress;
}
