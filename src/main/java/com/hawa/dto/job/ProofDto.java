package com.hawa.dto.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProofDto {

    @JsonProperty("signature_url")
    private String signatureUrl;

}
