package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobDropoffEtaResponseDto {

    @JsonProperty("seconds")
    private int dropoffEta;

}
