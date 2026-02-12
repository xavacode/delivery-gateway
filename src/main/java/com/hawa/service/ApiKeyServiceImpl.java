package com.hawa.service;

import com.hawa.model.ApiKey;
import com.hawa.model.Store;
import com.hawa.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

    private static final int KEY_LENGTH = 32;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createApiKeyForStore(Store store) {
        // Generate new API key info
        ApiKeyInfo keyInfo = generateApiKey();

        // Ensure prefix uniqueness (very low probability of collision)
        int attempts = 0;
        while (apiKeyRepository.existsByKeyPrefix(keyInfo.keyPrefix()) && attempts < 3) {
            keyInfo = generateApiKey();
            attempts++;
        }

        // Create and save API key entity
        ApiKey apiKey = new ApiKey();
        apiKey.setKeyHash(keyInfo.keyHash());
        apiKey.setKeyPrefix(keyInfo.keyPrefix());
        apiKey.setStore(store);
        apiKey.setActive(true);
        apiKey.setCreatedAt(LocalDateTime.now());

        apiKeyRepository.save(apiKey);

        log.info("Created new API key for store: {}", store.getId());

        // Return the raw key ONLY ONCE (it won't be stored again)
        return keyInfo.rawKey();
    }

    /**
     * Revoke/disable an API key
     */
    @Transactional
    public void revokeApiKey(long apiKeyId) {
        apiKeyRepository.findById(apiKeyId)
                .ifPresent(key -> {
                    key.setActive(false);
                    apiKeyRepository.save(key);
                    log.info("Revoked API key: {}", apiKeyId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiKey> getApiKeysForStore(long storeId) {
        return apiKeyRepository.getApiKeysByStore(storeId);
    }

    public Optional<Store> verifyApiKeyAndGetStore(String rawApiKey) {
        // Extract prefix for quick lookup
        String keyPrefix = extractKeyPrefix(rawApiKey);

        // Find all active API keys with this prefix
        List<ApiKey> potentialKeys = apiKeyRepository.findActiveByKeyPrefix(keyPrefix);

        // Verify each potential key (should be 0 or 1 due to prefix uniqueness)
        for (ApiKey apiKey : potentialKeys) {
            if (verifyApiKey(rawApiKey, apiKey.getKeyHash())) {
                // Update last used timestamp
                updateLastUsedAsync(apiKey);
                log.debug("Valid API key used for store: {}", apiKey.getStore().getId());

                return Optional.of(apiKey.getStore());
            }
        }

        log.warn("Invalid API key attempt");
        return Optional.empty();
    }

    /**
     * Generate a new secure API key
     */
    private ApiKeyInfo generateApiKey() {
        String rawKey = generateSecureRandomKey();
        String keyHash = hashApiKey(rawKey);
        String keyPrefix = rawKey.substring(0, 8);
        return new ApiKeyInfo(rawKey, keyHash, keyPrefix);
    }

    /**
     * Hash an API key for storage
     */
    private String hashApiKey(String rawApiKey) {
        return passwordEncoder.encode(rawApiKey);
    }

    /**
     * Verify if a raw API key matches the stored hash
     */
    private boolean verifyApiKey(String rawApiKey, String storedHash) {
        return passwordEncoder.matches(rawApiKey, storedHash);
    }

    /**
     * Generate a cryptographically secure random API key
     */
    private String generateSecureRandomKey() {
        byte[] bytes = new byte[KEY_LENGTH]; // 256-bit key
        SECURE_RANDOM.nextBytes(bytes);
        // Encode to base64 without padding for cleaner URL usage
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(bytes);
    }

    private String extractKeyPrefix(String rawApiKey) {
        // Extract first 8 characters as prefix
        return rawApiKey.length() >= 8 ?
                rawApiKey.substring(0, 8) : rawApiKey;
    }

    @Async
    @Transactional
    public void updateLastUsedAsync(ApiKey apiKey) {
        apiKey.setLastUsedAt(LocalDateTime.now());
        apiKeyRepository.save(apiKey);
    }

    public record ApiKeyInfo(
            String rawKey,
            String keyHash,
            String keyPrefix
            ) {
    }
}
