package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JobCancellationRequestDto {

    private String comment;
    @JsonProperty("public_reason_key")
    private String publicReasonKey;
}
