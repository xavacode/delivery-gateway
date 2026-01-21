package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobCancellationRequestDto {

    private String comment;
    @JsonProperty("public_reason_key")
    private String publicReasonKey;
}
