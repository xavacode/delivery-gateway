package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CancellationDto {

    @JsonProperty("canceled_by")
    private String canceledBy;

    @JsonProperty("reason_key")
    private String reasonKey;

    private String comment;

}
