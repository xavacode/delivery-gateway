CREATE TABLE api_key (
    id BIGINT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    key_hash VARCHAR(255) NOT NULL,
    key_prefix VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_used_at TIMESTAMP NULL,
    INDEX idx_api_key_store_id (store_id),
    INDEX idx_api_key_active (active),
    INDEX idx_api_key_created_at (created_at),
    INDEX idx_api_key_prefix (key_prefix),
    CONSTRAINT fk_api_key_store
            FOREIGN KEY (store_id)
            REFERENCES store(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;