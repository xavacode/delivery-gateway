package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Dropoff {

    @JsonProperty("package_type")
    private String packageType;
    @JsonProperty("package_description")
    private String packageDescription;
    @JsonProperty("client_reference")
    private String clientReference;
    private String address;
    private String comment;
    private ContactDto contact;
}
