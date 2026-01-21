package com.hawa.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class TokenDto {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("created_at")
    private Long createdAt;

    private String scope;

    public Instant getExpiresAt() {
        return Instant.ofEpochSecond(createdAt).plusSeconds(expiresIn);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(getExpiresAt());
    }

    public boolean willExpireWithin(long seconds) {
        return getExpiresAt().minusSeconds(seconds).isBefore(Instant.now());
    }

}