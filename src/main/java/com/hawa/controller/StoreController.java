package com.hawa.controller;

import com.hawa.dto.store.StoreCreateRequestDto;
import com.hawa.mapper.StoreMapper;
import com.hawa.model.Store;
import com.hawa.service.ApiKeyService;
import com.hawa.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreMapper storeMapper;
    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyManagementController.ApiKeyResponse> saveStore(@RequestBody StoreCreateRequestDto storeCreateRequestDto) {
        Store store = storeService.saveStore(storeMapper.storeCreateRequestDtoToStore(storeCreateRequestDto));
        String rawApiKey = apiKeyService.createApiKeyForStore(store);
        ApiKeyManagementController.ApiKeyResponse response = new ApiKeyManagementController.ApiKeyResponse(
                rawApiKey, // This is the ONLY time the raw key is exposed
                store.getId(),
                store.getName(),
                store.getCreatedAt()
        );
        return ResponseEntity.ok(response);
    }

}
