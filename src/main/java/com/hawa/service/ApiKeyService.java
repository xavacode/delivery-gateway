package com.hawa.service;

import com.hawa.dto.ApiKeyInfo;
import com.hawa.model.ApiKey;
import com.hawa.model.Store;

import java.util.List;
import java.util.Optional;

public interface ApiKeyService {

    Optional<Store> verifyApiKeyAndGetStore(String rawApiKey);
    void updateLastUsedAsync(ApiKey apiKey);
    String createApiKeyForStore(Store store);
    void revokeApiKey(long apiKeyId);
    List<ApiKey> getApiKeysForStore(long storeId);
}
