package com.hawa.service;

import com.hawa.dto.token.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class TokenStorageServiceImpl implements TokenStorageService {
   
    private final AtomicReference<TokenDto> tokenRef = new AtomicReference<>();
    private final AtomicInteger refreshCount = new AtomicInteger();
    private final AtomicInteger hitCount = new AtomicInteger();
    private final AtomicInteger missCount = new AtomicInteger();

    /**
     * Atomic get or refresh operation
     */
    @Override
    public String getOrRefreshBearerToken(TokenFetcher fetcher) {
        // Fast path: check if token exists and is valid
        TokenDto current = tokenRef.get();
        if (current != null && !current.isExpired()) {
            hitCount.incrementAndGet();
            return "Bearer " + current.getAccessToken();
        }

        // Need to refresh
        missCount.incrementAndGet();
        return refreshAndGetToken(fetcher);
    }

    @Override
    public String refreshAndGetToken(TokenFetcher fetcher) {
        TokenDto newToken = null;
        try {
            newToken = fetcher.fetch();

            // Try to update atomically
            TokenDto current = tokenRef.get();
            if (current == null || current.isExpired()) {
                if (tokenRef.compareAndSet(current, newToken)) {
                    refreshCount.incrementAndGet();
                    log.info("Token refreshed successfully");
                }
            }

            // Return the token we just fetched (even if another thread won the race)
            TokenDto finalToken = tokenRef.get();
            if (finalToken == null) {
                // Shouldn't happen, but fallback
                tokenRef.set(newToken);
                finalToken = newToken;
            }

            return "Bearer " + finalToken.getAccessToken();

        } catch (Exception e) {
            log.error("Failed to refresh token", e);
            throw new TokenRefreshException("Failed to refresh access token", e);
        }
    }

    /**
     * Force refresh token (for manual refresh or on 401)
     */
    @Override
    public String forceRefreshToken(TokenFetcher fetcher) {
        log.info("Force refreshing token");
        TokenDto newToken = fetcher.fetch();
        tokenRef.set(newToken);
        refreshCount.incrementAndGet();
        return "Bearer " + newToken.getAccessToken();
    }

    /**
     * Get token metrics for monitoring
     */
    @Override
    public TokenMetrics getMetrics() {
        TokenDto token = tokenRef.get();
        return new TokenMetrics(
                hitCount.get(),
                missCount.get(),
                refreshCount.get(),
                token != null ? token.getExpiresAt() : null,
                token != null && !token.isExpired()
        );
    }

    /**
     * Clear token (for logout or error scenarios)
     */
    @Override
    public void clearToken() {
        tokenRef.set(null);
        log.info("Token cleared");
    }

}
