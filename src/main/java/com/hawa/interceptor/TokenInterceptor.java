package com.hawa.interceptor;

import com.hawa.service.TokenService;
import com.hawa.service.TokenServiceImpl;
import com.hawa.service.TokenStorageService;
import com.hawa.service.TokenStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    private final TokenService tokenService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        String token = tokenService.getValidToken();
        request.getHeaders().set("Authorization", token);

        ClientHttpResponse response = execution.execute(request, body);

        if (response.getStatusCode().value() == 401) {
            logger.warn("Received 401, refreshing token and retrying");
            response.close();

            String newToken = tokenService.refreshToken();
            request.getHeaders().set("Authorization", newToken);

            return execution.execute(request, body);
        }

        return response;
    }
}
