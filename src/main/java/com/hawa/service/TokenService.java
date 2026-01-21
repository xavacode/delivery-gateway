package com.hawa.service;

import com.hawa.dto.token.TokenDto;

public interface TokenService {
    String getValidToken();

    TokenDto fetchNewToken();

    String refreshToken();

    TokenStorageServiceImpl.TokenMetrics getTokenMetrics();

    void clearToken();
}
