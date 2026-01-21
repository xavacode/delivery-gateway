package com.hawa.service;

import com.hawa.dto.token.TokenDto;

public interface TokenStorageService {

    String getOrRefreshBearerToken(TokenFetcher fetcher);

    String refreshAndGetToken(TokenFetcher fetcher);

    String forceRefreshToken(TokenFetcher fetcher);

    TokenMetrics getMetrics();

    void clearToken();

    @FunctionalInterface
    public interface TokenFetcher {
        TokenDto fetch();
    }

    public static class TokenRefreshException extends RuntimeException {
        public TokenRefreshException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public record TokenMetrics(
            int hitCount,
            int missCount,
            int refreshCount,
            java.time.Instant expiresAt,
            boolean isValid
    ) {
    }
}
