package com.hawa.service;

import com.hawa.dto.token.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ApiClientService {
    private static final Logger logger = LoggerFactory.getLogger(ApiClientService.class);

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    /**
     * Example API call - token is automatically added by interceptor
     */
    public TokenDto fetchData() {
        String url = "https://api.example.com/v1/data";

        ResponseEntity<TokenDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                TokenDto.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        throw new RuntimeException("Failed to fetch data: " + response.getStatusCode());
    }

    /**
     * Manual token usage example
     */
    public TokenDto fetchDataManually() {
        String url = "https://api.example.com/v1/data";

        // Get token manually
        String token = tokenService.getValidToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<TokenDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                TokenDto.class
        );

        return response.getBody();
    }

    /**
     * Batch operation with same token
     */
    public void batchOperation() {
        // Get token once for batch
        String token = tokenService.getValidToken();

        // Use the same token for multiple calls
        for (int i = 0; i < 10; i++) {
            makeApiCallWithToken(token, i);
        }
    }

    private void makeApiCallWithToken(String token, int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        restTemplate.exchange(
                "https://api.example.com/v1/items/" + id,
                HttpMethod.GET,
                request,
                String.class
        );
    }
}
