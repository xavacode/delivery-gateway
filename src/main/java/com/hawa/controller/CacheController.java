package com.hawa.controller;


import com.hawa.config.CacheConfiguration.CacheMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Cache Utility")
@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
@Slf4j
public class CacheController {

    private final CacheManager cacheManager;
    private final MeterRegistry meterRegistry;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();

        cacheManager.getCacheNames().forEach(cacheName -> {
            org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
            if (cache != null && cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                        (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache.getNativeCache();

                com.github.benmanes.caffeine.cache.stats.CacheStats cacheStats = nativeCache.stats();

                Map<String, Object> cacheStat = new HashMap<>();
                cacheStat.put("size", nativeCache.estimatedSize());
                cacheStat.put("hitCount", cacheStats.hitCount());
                cacheStat.put("missCount", cacheStats.missCount());
                cacheStat.put("hitRate", cacheStats.hitRate());
                cacheStat.put("evictionCount", cacheStats.evictionCount());
                cacheStat.put("loadSuccessCount", cacheStats.loadSuccessCount());
                cacheStat.put("loadFailureCount", cacheStats.loadFailureCount());
                cacheStat.put("totalLoadTime", cacheStats.totalLoadTime());

                stats.put(cacheName, cacheStat);
            }
        });

        // Add application-level cache metrics
        stats.put("applicationMetrics", CacheMetrics.getStats());

        return ResponseEntity.ok(stats);
    }

    @PostMapping("/clear/{cacheName}")
    public ResponseEntity<String> clearCache(@PathVariable String cacheName) {
        org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            log.info("Manually cleared cache: {}", cacheName);
            return ResponseEntity.ok("Cache '" + cacheName + "' cleared successfully");
        }
        return ResponseEntity.badRequest().body("Cache '" + cacheName + "' not found");
    }

    @PostMapping("/clear-all")
    public ResponseEntity<String> clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        });
        log.info("Manually cleared all caches");
        return ResponseEntity.ok("All caches cleared successfully");
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> cacheHealth() {
        Map<String, Object> health = new HashMap<>();
        boolean healthy = true;

        for (String cacheName : cacheManager.getCacheNames()) {
            org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
            if (cache != null && cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                        (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache.getNativeCache();

                com.github.benmanes.caffeine.cache.stats.CacheStats stats = nativeCache.stats();
                double hitRate = stats.hitRate();

                health.put(cacheName + ".hitRate", hitRate);
                health.put(cacheName + ".size", nativeCache.estimatedSize());

                if (hitRate < 0.3) { // Alert if hit rate below 30%
                    healthy = false;
                    health.put(cacheName + ".status", "WARNING - Low hit rate: " + (hitRate * 100) + "%");
                } else {
                    health.put(cacheName + ".status", "HEALTHY");
                }
            }
        }

        health.put("overallStatus", healthy ? "HEALTHY" : "DEGRADED");
        health.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(health);
    }

    /**
     * Health indicator for cache
     */
    @Bean
    public HealthIndicator cacheHealthIndicator() {
        return () -> {
            try {
                Map<String, Object> details = new HashMap<>();
                boolean healthy = true;

                for (String cacheName : cacheManager.getCacheNames()) {
                    org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
                    if (cache != null) {
                        details.put(cacheName + ".exists", true);

                        if (cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
                            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                                    (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache.getNativeCache();

                            double hitRate = nativeCache.stats().hitRate();
                            details.put(cacheName + ".hitRate", hitRate);

                            if (hitRate < 0.1) { // Very low hit rate
                                healthy = false;
                            }
                        }
                    } else {
                        details.put(cacheName + ".exists", false);
                        healthy = false;
                    }
                }

                if (healthy) {
                    return Health.up().withDetails(details).build();
                } else {
                    return Health.down().withDetails(details).build();
                }
            } catch (Exception e) {
                return Health.down(e).build();
            }
        };
    }
}
