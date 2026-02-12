package com.hawa.repository;

import com.hawa.model.ApiKey;
import com.hawa.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    /**
     * Find by key prefix first (for performance)
     * Then verify the hash in application layer
     */
    @Query("SELECT ak FROM ApiKey ak " +
            "JOIN FETCH ak.store s " +
            "WHERE ak.keyPrefix = :keyPrefix " +
            "AND ak.active = true ")
    List<ApiKey> findActiveByKeyPrefix(@Param("keyPrefix") String keyPrefix);

    /**
     * Check if a key prefix exists (for uniqueness)
     */
    boolean existsByKeyPrefix(String keyPrefix);

    @Query("SELECT ak FROM ApiKey ak WHERE ak.store.id = :storeId ")
    List<ApiKey> getApiKeysByStore(@Param("storeId") long storeId);
}
