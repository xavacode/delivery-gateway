package com.hawa.dto.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StuartWebhookDetails {

    @JsonProperty("account")
    private StuartAccount account;

    @JsonProperty("package")
    private StuartPackage pkg;  // Note: 'package' is a reserved word
}
