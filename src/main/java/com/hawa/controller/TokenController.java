package com.hawa.controller;

import com.hawa.service.TokenService;
import com.hawa.service.TokenStorageServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/status")
    public ResponseEntity<TokenStatus> getTokenStatus() {
        TokenStorageServiceImpl.TokenMetrics metrics = tokenService.getTokenMetrics();

        TokenStatus status = new TokenStatus(
                metrics.isValid(),
                metrics.expiresAt(),
                metrics.hitCount(),
                metrics.missCount(),
                metrics.refreshCount()
        );

        return ResponseEntity.ok(status);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshToken() {
        String newToken = tokenService.refreshToken();
        return ResponseEntity.ok("Token refreshed successfully " + newToken);
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearToken() {
        tokenService.clearToken();
        return ResponseEntity.ok("Token cleared");
    }

    public record TokenStatus(
            boolean valid,
            java.time.Instant expiresAt,
            int hits,
            int misses,
            int refreshes
    ) {
    }
}
