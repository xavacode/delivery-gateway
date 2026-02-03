package com.hawa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.hawa.generator.PayloadKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class CacheConfiguration {

    @Autowired
    private CacheProperties cacheProperties;

    @Value("${spring.application.name:application}")
    private String applicationName;

    @Bean("payloadKeyGenerator")
    public PayloadKeyGenerator payloadKeyGenerator(ObjectMapper objectMapper) {
        return new PayloadKeyGenerator(objectMapper);
    }

    /**
     * Primary Cache Manager for the application
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAllowNullValues(false); // Don't cache null values

        // Set default specification (fallback)
        cacheManager.setCacheSpecification("maximumSize=10000,expireAfterWrite=600s,recordStats");

        // Register all caches with their specific configurations
        Map<String, Caffeine<Object, Object>> builders = new HashMap<>();

        cacheProperties.getCaches().forEach((cacheName, config) -> {
            Caffeine<Object, Object> builder = Caffeine.newBuilder();

            // Set maximum size
            if (config.getMaxSize() > 0) {
                builder.maximumSize(config.getMaxSize());
            }

            // Set TTL based on configuration
            if (config.isExpireAfterAccess()) {
                builder.expireAfterAccess(config.getTtl(), TimeUnit.SECONDS);
            } else {
                builder.expireAfterWrite(config.getTtl(), TimeUnit.SECONDS);
            }

            // Refresh after write for hot caches
            if (config.getRefreshAfterWrite() > 0) {
                builder.refreshAfterWrite(config.getRefreshAfterWrite(), TimeUnit.SECONDS);
            }

            // Record statistics
            if (config.isRecordStats()) {
                builder.recordStats(() -> new CacheStatsCollector(cacheName));
            }

            // Use soft values if configured (allows GC to evict under memory pressure)
            if (config.isSoftValues()) {
                builder.softValues();
            }

            // Add removal listener for monitoring
            builder.removalListener((key, value, cause) -> {
                if (cause.wasEvicted()) {
                    log.debug("Cache '{}' evicted key: {} due to: {}", cacheName, key, cause);
                    CacheMetrics.recordEviction(cacheName, cause);
                }
            });
            cacheManager.registerCustomCache(cacheName, builder.build());
        });

        // Initialize caches
        cacheManager.setCacheNames(cacheProperties.getCaches().keySet());

        return cacheManager;
    }

    /**
     * Async Cache Manager for non-blocking operations
     */
    @Bean("asyncCacheManager")
    @ConditionalOnProperty(name = "app.cache.async.enabled", havingValue = "true")
    public CacheManager asyncCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheSpecification("maximumSize=5000,expireAfterWrite=300s,recordStats");
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

    /**
     * Scheduled task to log cache statistics
     */
//    @Scheduled(fixedDelayString = "${app.cache.monitoring.stats-log-interval:300000}")
    public void logCacheStatistics() {
        CacheManager cacheManager = cacheManager();
        if (cacheManager instanceof CaffeineCacheManager) {
            CaffeineCacheManager cm = (CaffeineCacheManager) cacheManager;

            cm.getCacheNames().forEach(cacheName -> {
                org.springframework.cache.Cache cache = cm.getCache(cacheName);
                if (cache != null && cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
                    com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache.getNativeCache();

                    CacheStats stats = nativeCache.stats();
                    long size = nativeCache.estimatedSize();
                    double hitRate = stats.hitRate();

                    // Log cache statistics
                    log.info("Cache Stats - {}: Size={}, HitRate={:.2f}%, Hits={}, Misses={}, Evictions={}, LoadSuccess={}, LoadFailures={}", cacheName, size, hitRate * 100, stats.hitCount(), stats.missCount(), stats.evictionCount(), stats.loadSuccessCount(), stats.loadFailureCount());

                    // Alert on low hit rate
                    if (hitRate < cacheProperties.getMonitoring().getAlertHitRateThreshold()) {
                        log.warn("LOW CACHE HIT RATE - {}: {:.2f}% is below threshold of {:.0f}%", cacheName, hitRate * 100, cacheProperties.getMonitoring().getAlertHitRateThreshold() * 100);
                    }

                    // Alert on high eviction rate
                    if (stats.evictionCount() > cacheProperties.getMonitoring().getAlertEvictionThreshold()) {
                        log.warn("HIGH EVICTION RATE - {}: {} evictions in period", cacheName, stats.evictionCount());
                    }
                }
            });
        }
    }

    /**
     * Cache configuration properties
     */
    @Configuration
    @ConfigurationProperties(prefix = "app.cache")
    @Slf4j
    public static class CacheProperties {
        private double maxHeapPercentage = 0.35;
        private Map<String, CacheConfig> caches = new HashMap<>();
        private EvictionConfig eviction = new EvictionConfig();
        private MonitoringConfig monitoring = new MonitoringConfig();

        // Getters and Setters
        public double getMaxHeapPercentage() {
            return maxHeapPercentage;
        }

        public void setMaxHeapPercentage(double maxHeapPercentage) {
            this.maxHeapPercentage = maxHeapPercentage;
        }

        public Map<String, CacheConfig> getCaches() {
            return caches;
        }

        public void setCaches(Map<String, CacheConfig> caches) {
            this.caches = caches;
        }

        public EvictionConfig getEviction() {
            return eviction;
        }

        public void setEviction(EvictionConfig eviction) {
            this.eviction = eviction;
        }

        public MonitoringConfig getMonitoring() {
            return monitoring;
        }

        public void setMonitoring(MonitoringConfig monitoring) {
            this.monitoring = monitoring;
        }

        @lombok.Data
        public static class CacheConfig {
            private long maxSize = 10000;
            private long ttl = 600; // seconds
            private long refreshAfterWrite = 0; // seconds
            private boolean expireAfterAccess = false;
            private boolean recordStats = true;
            private boolean softValues = false;
            private String keyPrefix = "";
        }

        @lombok.Data
        public static class EvictionConfig {
            private String policy = "size-based";
            private long cleanupDelay = 1000; // milliseconds
            private double maxWeight = 0.0; // for weight-based eviction
        }

        @lombok.Data
        public static class MonitoringConfig {
            private boolean enabled = true;
            private long statsLogInterval = 300; // seconds
            private double alertHitRateThreshold = 0.7;
            private long alertEvictionThreshold = 1000;
        }
    }

    /**
     * Custom cache stats collector
     */
    private static class CacheStatsCollector implements com.github.benmanes.caffeine.cache.stats.StatsCounter {
        private final String cacheName;
        private final Map<RemovalCause, Long> evictionCounts = new ConcurrentHashMap<>();

        public CacheStatsCollector(String cacheName) {
            this.cacheName = cacheName;
        }

        @Override
        public void recordHits(int count) {
            CacheMetrics.recordHits(cacheName, count);
        }

        @Override
        public void recordMisses(int count) {
            CacheMetrics.recordMisses(cacheName, count);
        }

        @Override
        public void recordLoadSuccess(long loadTime) {
            CacheMetrics.recordLoadSuccess(cacheName, loadTime);
        }

        @Override
        public void recordLoadFailure(long loadTime) {
            CacheMetrics.recordLoadFailure(cacheName, loadTime);
        }

        @Override
        public void recordEviction(int weight, RemovalCause cause) {
            evictionCounts.merge(cause, 1L, Long::sum);
            CacheMetrics.recordEviction(cacheName, cause);
        }

        @Override
        public CacheStats snapshot() {
            return CacheStats.of(0, 0, 0, 0, 0, 0, 0); // Not used
        }
    }

    /**
     * Cache metrics utility
     */
    public static class CacheMetrics {
        private static final Map<String, Long> hitCounts = new ConcurrentHashMap<>();
        private static final Map<String, Long> missCounts = new ConcurrentHashMap<>();
        private static final Map<String, Map<RemovalCause, Long>> evictionCounts = new ConcurrentHashMap<>();

        public static void recordHits(String cacheName, int count) {
            hitCounts.merge(cacheName, (long) count, Long::sum);
        }

        public static void recordMisses(String cacheName, int count) {
            missCounts.merge(cacheName, (long) count, Long::sum);
        }

        public static void recordLoadSuccess(String cacheName, long loadTime) {
            // Could record to metrics system
        }

        public static void recordLoadFailure(String cacheName, long loadTime) {
            // Could record to metrics system
        }

        public static void recordEviction(String cacheName, RemovalCause cause) {
            evictionCounts.computeIfAbsent(cacheName, k -> new ConcurrentHashMap<>()).merge(cause, 1L, Long::sum);
        }

        public static Map<String, Object> getStats() {
            Map<String, Object> stats = new HashMap<>();

            hitCounts.forEach((cache, hits) -> {
                Long misses = missCounts.getOrDefault(cache, 0L);
                double hitRate = (hits + misses) > 0 ? (double) hits / (hits + misses) : 0.0;

                stats.put(cache + ".hits", hits);
                stats.put(cache + ".misses", misses);
                stats.put(cache + ".hitRate", hitRate);

                Map<RemovalCause, Long> evictions = evictionCounts.get(cache);
                if (evictions != null) {
                    stats.put(cache + ".evictions", evictions);
                }
            });

            return stats;
        }
    }
}