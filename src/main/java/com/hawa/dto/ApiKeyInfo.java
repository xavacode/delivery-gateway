package com.hawa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiKeyInfo {
    @JsonProperty("apiKey")
    private final String rawKey;
    @JsonIgnore
    private final String keyHash;
    @JsonIgnore
    private final String keyPrefix;
}
