package com.hawa.filter;

import com.hawa.model.Store;
import com.hawa.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final ApiKeyService apiKeyService;

    private static final String STORE_ATTRIBUTE = "currentStore";
    private static final String API_KEY_ATTRIBUTE = "apiKeyPrefix";

    private static final String[] PUBLIC_PATHS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/health",
            "/api/stores",
            "/actuator/**",
            "/trackings/**",
            "/webhooks/**"
    };
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if this is a public path
        String requestPath = request.getRequestURI();

        if (shouldSkipFilter(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String rawApiKey = extractApiKey(request);

        if (rawApiKey == null) {
            sendUnauthorized(response, "Missing API Key");
            return;
        }

        // Validate API key against hashed version in DB
        Optional<Store> storeOptional = apiKeyService.verifyApiKeyAndGetStore(rawApiKey);

        if (storeOptional.isEmpty()) {
            sendUnauthorized(response, "Invalid API Key");
            return;
        }

        // Store the authenticated store in request context
        Store store = storeOptional.get();
        request.setAttribute(STORE_ATTRIBUTE, store);
        request.setAttribute(API_KEY_ATTRIBUTE, rawApiKey.substring(0, Math.min(8, rawApiKey.length())));

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String extractApiKey(HttpServletRequest request) {
        // Try Authorization header first
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            if (authHeader.startsWith("ApiKey ")) {
                return authHeader.substring(7);
            }
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7); // Also support Bearer prefix
            }
        }

        // Fallback to X-API-KEY header
        return request.getHeader("X-API-KEY");
    }

    private void sendUnauthorized(HttpServletResponse response, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"error\": \"%s\", \"timestamp\": \"%s\"}",
                        message, LocalDateTime.now()));
    }

    private boolean shouldSkipFilter(String requestPath) {
        // Check if path matches any public pattern
        for (String publicPath : PUBLIC_PATHS) {
            if (publicPath.endsWith("/**")) {
                String prefix = publicPath.substring(0, publicPath.length() - 3);
                if (requestPath.startsWith(prefix)) {
                    return true;
                }
            } else if (publicPath.equals(requestPath)) {
                return true;
            }
        }
        return false;
    }
}
