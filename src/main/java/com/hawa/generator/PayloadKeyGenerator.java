package com.hawa.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@Slf4j
public class PayloadKeyGenerator implements KeyGenerator {

    @PostConstruct
    public void init() {
        log.info("✅ PayloadKeyGenerator bean initialized: {}", this.getClass().getName());
    }

    private final ObjectMapper objectMapper;

    public PayloadKeyGenerator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object generate(Object target, Method method, Object... params) {
        // Build cache key from method signature and parameters
        StringBuilder keyBuilder = new StringBuilder();

        // 1. Add class and method
        keyBuilder.append(target.getClass().getSimpleName())
                .append(".")
                .append(method.getName());

        // 2. Add parameter hash
        for (Object param : params) {
            if (param != null) {
                try {
                    String paramJson = objectMapper.writeValueAsString(param);
                    String hash = DigestUtils.md5DigestAsHex(paramJson.getBytes(StandardCharsets.UTF_8));
                    keyBuilder.append(":").append(hash);
                } catch (JsonProcessingException e) {
                    // Fallback to simple hash code
                    keyBuilder.append(":").append(param.hashCode());
                }
            } else {
                keyBuilder.append(":null");
            }
        }

        String key = keyBuilder.toString();
        log.debug("Generated cache key: {}", key);
        return key;
    }
}
