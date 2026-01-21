package com.hawa.service;

import com.hawa.config.StuartApiConfig;
import com.hawa.dto.token.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private final RestTemplate restTemplate;
    private final TokenStorageService tokenStorageService;
    private final StuartApiConfig stuartApiConfig;

    public TokenServiceImpl(@Qualifier("restTemplateToFetchToken") RestTemplate restTemplate,
                            TokenStorageService tokenStorageService,
                            StuartApiConfig stuartApiConfig){
        this.restTemplate=restTemplate;
        this.tokenStorageService=tokenStorageService;
        this.stuartApiConfig=stuartApiConfig;
    }

    /**
     * Get valid bearer token - fetches if expired or doesn't exist
     */
    @Override
    public String getValidToken() {
        return tokenStorageService.getOrRefreshBearerToken(this::fetchNewToken);
    }

    /**
     * Fetch new token from API (actual HTTP call)
     */
    @Override
    public TokenDto fetchNewToken() {
        log.info("Fetching new token from API");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(stuartApiConfig.getClientId(), stuartApiConfig.getClientSecret());

        String requestBody = "grant_type=client_credentials&scope=api";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<TokenDto> response = restTemplate.exchange(
                    stuartApiConfig.getAuthUrl(),
                    HttpMethod.POST,
                    request,
                    TokenDto.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                TokenDto token = response.getBody();
                log.info("Token fetched successfully, expires in {} seconds", token.getExpiresIn());
                return token;
            }

            throw new RuntimeException("Failed to fetch token: " + response.getStatusCode());

        } catch (Exception e) {
            log.error("Error fetching token from {}", stuartApiConfig.getAuthUrl(), e);
            throw new TokenStorageServiceImpl.TokenRefreshException("Failed to fetch API token", e);
        }
    }

    /**
     * Force token refresh (e.g., on 401)
     */
    @Override
    public String refreshToken() {
        return tokenStorageService.forceRefreshToken(this::fetchNewToken);
    }

    /**
     * Get token metrics for monitoring
     */
    @Override
    public TokenStorageServiceImpl.TokenMetrics getTokenMetrics() {
        return tokenStorageService.getMetrics();
    }


    /**
     * Clear current token
     */
    @Override
    public void clearToken() {
        tokenStorageService.clearToken();
    }
}
