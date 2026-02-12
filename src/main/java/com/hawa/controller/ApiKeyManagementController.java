package com.hawa.controller;

import com.hawa.dto.ApiKeyInfo;
import com.hawa.model.Store;
import com.hawa.service.ApiKeyService;
import com.hawa.service.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "ApiKey Manager")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ApiKeyManagementController {

    private final ApiKeyService apiKeyService;
    private final StoreService storeService;

    /**
     * Generate a new API key for a store
     * IMPORTANT: The raw key is returned ONLY ONCE here
     */
    @PostMapping("/stores/{storeId}/api-keys")
    public ResponseEntity<ApiKeyResponse> createApiKey(
            @PathVariable Long storeId) {

        Store store = storeService.getStore(storeId);

        String rawApiKey =
                apiKeyService.createApiKeyForStore(store);

        // Create response with the raw key (shown only once!)
        ApiKeyResponse response = new ApiKeyResponse(
                rawApiKey, // This is the ONLY time the raw key is exposed
                store.getId(),
                store.getName(),
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Revoke an API key
     */
    @DeleteMapping("/api-keys/{apiKeyId}")
    public ResponseEntity<Void> revokeApiKey(@PathVariable Long apiKeyId) {
        apiKeyService.revokeApiKey(apiKeyId);
        return ResponseEntity.noContent().build();
    }

    /**
     * List all API keys for a store (without showing hashes)
     */
    @GetMapping("/stores/{storeId}/api-keys")
    public ResponseEntity<List<ApiKeySummary>> listApiKeys(
            @PathVariable Long storeId) {

        List<ApiKeySummary> summaries = apiKeyService.getApiKeysForStore(storeId)
                .stream()
                .map(apiKey -> new ApiKeySummary(
                        apiKey.getId(),
                        apiKey.getKeyPrefix() + "***", // Only show prefix
                        apiKey.getCreatedAt(),
                        apiKey.getLastUsedAt(),
                        apiKey.isActive()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(summaries);
    }

    public record ApiKeyResponse(
            String apiKey,
            Long storeId,
            String storeName,
            LocalDateTime createdAt
    ) {
    }

    public record ApiKeySummary(
            Long id,
            String maskedKey, // Only shows prefix
            LocalDateTime createdAt,
            LocalDateTime lastUsedAt,
            boolean active
    ) {
    }
}
